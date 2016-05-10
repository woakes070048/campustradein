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
import java.util.Map;

/**
 * Created by ifeify on 5/2/16.
 */
@Controller
public class HomePageController {
    @Inject
    private UserService userService;

    @Inject
    private FreemarkerTemplateEngine templateEngine;

    @Route
    public void handleRenderHomepage() {
        Spark.get("/", (request, response) -> {
        	Map<String, String> model = new HashMap<>();
            try {
				String sessionID = request.cookie("session");
				System.out.println("Session id: " + sessionID);
				if(sessionID != null && !sessionID.isEmpty()) {
				    User user = userService.findUserBySessionID(sessionID);
				    model.put("user_name", user.getUsername());
				    if(user.isActivated()) {
				        model.put("user_active", "true");
				    } else {
				        model.put("user_active", "false");
				    }
				}
			} finally {
				model.put("signup_url", Routes.SIGNUP);
				model.put("forgotpassword_url", Routes.RESET_PASSWORD);
				model.put("login_url", Routes.LOGIN);
				model.put("logout_url", Routes.LOGOUT);
				model.put("activate_account_url", Routes.ACTIVATE_ACCOUNT);
			}
            System.out.println(model);
            return templateEngine.render(new ModelAndView(model, "welcome.ftl"));
        });
    }
}
