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
import com.google.gson.JsonObject;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
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
    public void checkIfUsernameExists() {
        Spark.get("/signupok/username/:username", (request, response) -> {
            Optional<String> username = Optional.ofNullable(request.params(":username"));
            if(username.isPresent() && !userService.isUsernameRegistered(username.get())) {
                response.status(HttpStatus.SC_OK);
            } else {
                response.status(HttpStatus.SC_BAD_REQUEST);
            }
            response.body("");
            response.header("Content-Type", "application/json");
            return null;
        });
    }

    @Route
    public void checkIfEmailExists() {
        Spark.get("/signupok/email/:email", (request, response) -> {
            Optional<String> email = Optional.ofNullable(request.params(":email"));
            if(email.isPresent() && !userService.isEmailRegistered(email.get())) {
                response.status(HttpStatus.SC_OK);
            } else {
                response.status(HttpStatus.SC_BAD_REQUEST);
            }
            response.body("");
            return null;
        });
    }

    @Route
    public void handleSignup() {
        Spark.before(Routes.SIGNUP, new RequiresJsonFilter());
        Spark.before(Routes.SIGNUP, new RequiresBodyFilter());

        Spark.post(Routes.SIGNUP, (request, response) -> {
            try {
                UserAccount userAccount = gson.fromJson(request.body(), UserAccount.class);
                Set<ConstraintViolation<UserAccount>> violations = validator.validate(userAccount);
                if(violations.size() > 0) { // errors with input
                    List<String> errors = new ArrayList<>();
                    for(ConstraintViolation v : violations) {
                        errors.add(v.getMessage());
                    }
                    response.status(HttpStatus.SC_BAD_REQUEST);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.append("errors", errors);
                    return jsonObject;
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
				sb.append(System.getProperty("hostname"));
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
                response.status(HttpStatus.SC_CREATED);
                response.header("Content-Type", "application/json");

                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("redirect", "/");
                return gson.toJson(jsonResponse);
            } catch(UserAlreadyExistsException e) {
                logger.error("An error occurred registering user", e);
                response.status(HttpStatus.SC_CONFLICT);
                response.header("Content-Type", "application/json");
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("error", "try using another username and/or email");
                return gson.toJson(jsonResponse);
            } catch(SMTPMailException | EncryptionException e) {
                logger.error("An error occurred creating new user account", e);
                response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            }
            return null;
        });
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
                    return templateEngine.render(new ModelAndView(model, "activation-success"));
                }
            } else {
                response.redirect("/resend-activation");
                return null;
            }
        });
    }

}
