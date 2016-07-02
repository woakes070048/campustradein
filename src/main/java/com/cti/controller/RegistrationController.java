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
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.validation.ConstraintViolation;
import java.nio.charset.Charset;
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
            // TODO: parse query string in request body
            List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(request.body().trim(), Charset.forName("UTF-8"));

            Map<String, String> queryParams = new HashMap<>();
            for(NameValuePair pair : nameValuePairs) {
                queryParams.put(pair.getName(), pair.getValue());
            }
            String type = queryParams.get("type");

            JsonObject jsonResponse = new JsonObject();

            if(type != null && type.equals("email")) {
                String email = queryParams.get("email");
                if(email != null && !userService.isEmailRegistered(email)) {
                    jsonResponse.addProperty("valid", true);
                } else {
                    jsonResponse.addProperty("valid", false);
                }
            } else if(type != null && type.equals("username")) {
                String username = queryParams.get("username");
                if(username != null && !userService.isUsernameRegistered(username)) {
                    jsonResponse.addProperty("valid", true);
                } else {
                    jsonResponse.addProperty("valid", false);
                }
            } else {
                jsonResponse.addProperty("valid", false);
            }
            response.header("Content-Type", "application/json");
            return gson.toJson(jsonResponse);
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
                    Map<String, String> errors = new HashMap<>();
                    for(ConstraintViolation v : violations) {
                        logger.error("Field {} has error {}", v.getPropertyPath(), v.getMessage());
                        errors.put(v.getPropertyPath().toString(), v.getMessage());
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
                response.cookie(Cookies.EMAIL, userAccount.getEmail());
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
            } catch(Exception e) {
                logger.error("Error occurred signing up", e);
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("result", "error");
                return jsonResponse;
            }
        }, gson::toJson);
    }

    @Route
    public void handleAccountActivation() {
        Spark.get(Routes.ACTIVATE_ACCOUNT, (request, response) -> {
            String tokenID = request.queryParams("q");
            Optional<VerificationToken> result = tokenRepository.findById(tokenID);
            if(result.isPresent()) {
                VerificationToken verificationToken = result.get();
                if(verificationToken.hasExpired()) {
                    response.redirect("/resend-activation");
                    return null;
                } else {
                    tokenRepository.deleteToken(tokenID);
                    userService.activateUser(verificationToken.getUsername());
                    Map<String, String> model = new HashMap<>();
                    model.put("username", verificationToken.getUsername());
                    return templateEngine.render(new ModelAndView(model, "accountActivated.ftl"));
                }
            } else {
                response.redirect("/");
                return null;
            }
        });
    }
}
