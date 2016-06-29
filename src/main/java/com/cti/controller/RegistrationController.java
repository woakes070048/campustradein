package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.auth.VerificationToken;
import com.cti.config.Routes;
import com.cti.exception.EncryptionException;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.UserAccount;
import com.cti.repository.SessionRepository;
import com.cti.repository.TokenRepository;
import com.cti.service.UserService;
import com.cti.smtp.Email;
import com.cti.smtp.SMTPMailException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * @author ifeify
 */
@Controller
public class RegistrationController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Inject
    private UserService userService;

    @Inject
    private SessionRepository sessionRepository;

    @Inject
    private TokenRepository tokenRepository;

    @Route
    public void validateUsernameAndEmail() {
        Spark.post("/signupok", (request, response) -> {
            response.header("Content-Type", "application/json");
            JsonObject jsonResponse = new JsonObject();

            String type = request.queryParams("type");
            jsonResponse.addProperty("valid", false);
            if(type != null && type.equals("email")) {
                String email = request.queryParams("email");
                if(email != null && !userService.isEmailRegistered(email)) {
                    jsonResponse.addProperty("valid", true);
                }
            }
            if(type != null && type.equals("username")) {
                String username = request.queryParams("username");
                if(username != null && !userService.isUsernameRegistered(username)) {
                    jsonResponse.addProperty("valid", true);
                }
            }
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonResponse);
            return gson.toJson(jsonArray);
        });
    }

    @Route
    public void handleSignup() {
        Spark.before(Routes.SIGNUP, new RequiresJsonFilter());
        Spark.before(Routes.SIGNUP, new RequiresBodyFilter());

        Spark.post(Routes.SIGNUP, (request, response) -> {
            try {
                System.out.println(request.body());
                UserAccount userAccount = gson.fromJson(request.body(), UserAccount.class);
                JsonObject jsonResponse = new JsonObject();
                Set<ConstraintViolation<UserAccount>> violations = validator.validate(userAccount);
                if(violations.size() > 0) { // errors with input
                    List<String> errors = new ArrayList<>();
                    for(ConstraintViolation v : violations) {
                        errors.add(v.getMessage());
                    }
                    jsonResponse.addProperty("result", "error");
//                    jsonResponse.addProperty("errors", errors);
                    return jsonResponse;
                }
                userService.createNewUser(userAccount);

                // send activation notification email to the user
                Email email = new Email();
                email.setTo(userAccount.getEmail());
                email.setFrom(sender);
                email.setSubject("Please verify your email");

                VerificationToken verificationToken = new VerificationToken(userAccount.getUsername());
                tokenRepository.addToken(verificationToken);
                StringBuilder sb = new StringBuilder();
				sb.append(System.getProperty("domainName"));
				sb.append(Routes.ACTIVATE_ACCOUNT);
				sb.append("?q=");
				sb.append(verificationToken.getToken());
				Map<String, String> model = new HashMap<>();
				model.put("username", userAccount.getUsername());
				model.put("activation_url", sb.toString());

                email.setBody(templateEngine.render(new ModelAndView(model, "activation_email.ftl")));
                userService.sendNotification(email);

                // start user session
                // TODO: load balancer will have to set HttpOnly and secure flags for cookies
                String sessionID = sessionRepository.newSession(userAccount.getUsername());
                response.cookie(Cookies.USER_SESSION, sessionID, 604800); // expires in a week
                response.cookie(Cookies.USER_NAME, userAccount.getUsername(), 604800);
                response.cookie(Cookies.LOGGED_IN, "yes", 604800);
                response.cookie(Cookies.SIGNUP_SUCCESS, "true");
                response.status(HttpStatus.SC_CREATED);
                response.header("Content-Type", "application/json");

                jsonResponse.addProperty("result", "ok");
                jsonResponse.addProperty("redirect", "/");
                return jsonResponse;
            } catch(UserAlreadyExistsException e) {
                logger.error("An error occurred registering user", e);

                response.header("Content-Type", "application/json");
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("result", "error");
                jsonResponse.addProperty("error", "try using another username and/or email");
                return jsonResponse;
            } catch(SMTPMailException | EncryptionException e) {
                logger.error("An error occurred creating new user account", e);
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("result", "error");
                jsonResponse.addProperty("error", "We cannot process your request at this time");
                return jsonResponse;
            }
        }, gson::toJson);
    }

    @Route
    public void handleAccountActivation() {
        Spark.post(Routes.ACTIVATE_ACCOUNT, (request, response) -> {
            String tokenID = request.queryParams("q");
            Optional<VerificationToken> result = tokenRepository.findById(tokenID);
            if(result.isPresent()) {
                VerificationToken verificationToken = result.get();
                if(verificationToken.hasExpired()) {
                    response.redirect("/resend-activation");
                    return null;
                } else {
                    tokenRepository.deleteToken(tokenID);
                    Map<String, String> model = new HashMap<>();
                    model.put("username", verificationToken.getUsername());
                    return templateEngine.render(new ModelAndView(model, "accountActivated.ftl"));
                }
            } else {
                response.redirect("/resend-activation");
                return null;
            }
        });
    }
}
