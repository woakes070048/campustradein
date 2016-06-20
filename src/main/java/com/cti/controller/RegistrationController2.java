package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.auth.AuthenticationToken;
import com.cti.config.Routes;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.UserAccount;
import com.cti.repository.SessionRepository;
import com.cti.repository.TokenRepository;
import com.cti.service.UserService2;
import com.cti.smtp.Email;
import com.cti.smtp.SMTPMailException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author ifeify
 */
@Controller
public class RegistrationController2 extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController2.class);

    @Inject
    private UserService2 userService2;

    @Inject
    private SessionRepository sessionRepository;

    @Inject
    private TokenRepository tokenRepository;

    @Route
    public void checkIfUsernameExists() {
        Spark.get("/users", (request, response) -> {
            Optional<String> result = Optional.ofNullable(request.queryParams("username"));
            if(result.isPresent()) {
                if(userService2.isUsernameRegistered(result.get())) {
                    response.status(HttpStatus.SC_OK);
                } else {
                    response.status(HttpStatus.SC_CONFLICT);
                }
            } else {
                response.status(HttpStatus.SC_CONFLICT);
            }
            return null;
        });
    }

    @Route
    public void checkIfEmailExists() {
        Spark.get("/users", (request, response) -> {
            Optional<String> result = Optional.ofNullable(request.queryParams("email"));
            if(result.isPresent()) {
                if(userService2.isUsernameRegistered(result.get())) {
                    response.status(HttpStatus.SC_OK);
                } else {
                    response.status(HttpStatus.SC_CONFLICT);
                }
            } else {
                response.status(HttpStatus.SC_CONFLICT);
            }
            return null;
        });
    }

    @Route
    public void hangleSignup() {
        Spark.post(Routes.SIGNUP, (request, response) -> {
            try {
                UserAccount userAccount = gson.fromJson(request.body(), UserAccount.class);
                userService2.createNewUser(userAccount);
                Email email = new Email();
                email.setTo(userAccount.getEmail());
                email.setFrom(sender);
                email.setSubject("Please verify your email");

                String token = tokenRepository.newToken(userAccount.getUsername());
                StringBuilder sb = new StringBuilder();
				sb.append(System.getProperty("hostname"));
				sb.append(Routes.ACTIVATE_ACCOUNT);
				sb.append("?token=");
				sb.append(token);
				Map<String, String> model = new HashMap<>();
				model.put("username", userAccount.getUsername());
				model.put("activation_url", sb.toString());

                email.setBody(templateEngine.render(new ModelAndView(model, "activation_email.ftl")));
                userService2.sendNotification(email);

                String sessionID = sessionRepository.newSession(userAccount.getUsername());
                response.cookie("user_session", sessionID);
                response.cookie("dotcom_user", userAccount.getUsername());
                response.cookie("loggedIn", "true");
                response.status(HttpStatus.SC_OK);
            } catch(UserAlreadyExistsException | SMTPMailException e) {
                logger.error("An error occurred registering user", e);
                response.status(HttpStatus.SC_BAD_REQUEST);
            }
            return null;
        });
    }

}
