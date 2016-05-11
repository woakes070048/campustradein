package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.config.FreemarkerTemplateEngine;
import com.cti.config.Routes;
import com.cti.model.User;
import com.cti.service.UserService;

import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;

import java.util.HashMap;
import java.text.MessageFormat;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ifeify on 5/2/16.
 */
@Controller
public class HomePageController {
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
    @Inject
    private UserService userService;

    @Inject
    private FreemarkerTemplateEngine templateEngine;

    @Route
    public void renderHomepage() {
        Spark.get("/", (request, response) -> {
        	Map<String, String> model = new HashMap<>();
            try {
				String sessionID = request.cookie("session");
                String emailVerified = request.cookie("email_verified");

				if(sessionID != null && !sessionID.isEmpty()) {
				    User user = userService.findUserBySessionID(sessionID);
					if(user != null) {
                        model.put("user_name", user.getUsername());
                        if(user.isActivated()) {
                            model.put("user_active", "true");
                            if(emailVerified != null) {
                                String message = MessageFormat.format("Hey {0}, thanks for activating your account. " +
                                        "Welcome to campustradein", user.getUsername());
                                model.put("important_message", message);
                                response.removeCookie("email_verified");
                            }
                        } else {
                            model.put("user_active", "false");
                            String message = MessageFormat.format("Hello {0}, an email was sent to you. Please verify your email address to activate your account",
                                                                    user.getUsername());
                            model.put("important_message", message);
                        }
                    }
				}
                model.put("signup_url", Routes.SIGNUP);
				model.put("forgotpassword_url", Routes.RESET_PASSWORD);
				model.put("login_url", Routes.LOGIN);
				model.put("logout_url", Routes.LOGOUT);
				model.put("activate_account_url", Routes.ACTIVATE_ACCOUNT);
                return templateEngine.render(new ModelAndView(model, "welcome.ftl"));
			} catch(Exception e) {
                logger.error("Failed to render homepage", e);
                response.redirect(Routes.ERROR);
                return null;
            }
        });
    }
}
