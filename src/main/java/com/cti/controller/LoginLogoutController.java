package com.cti.controller;

import javax.inject.Inject;

import org.apache.commons.lang3.StringEscapeUtils;

import spark.Spark;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.auth.AuthenticationToken;
import com.cti.model.User;
import com.cti.service.AuthenticationService;
import com.cti.service.UserService;

/**
 * Created by ifeify on 4/30/16.
 */
@Controller
public class LoginLogoutController {
	@Inject
    private UserService userService;
	
	@Inject
	private AuthenticationService authService;
	
	@Route
    public void handleLogout() {
        Spark.get("/logout", (request, response) -> {
        	String sessionID = request.cookie("session");
        	if(sessionID != null && !sessionID.isEmpty()) {
        		userService.endSession(sessionID);
        		response.removeCookie("session");
        	}
        	response.redirect("/");
            return null;
        });
    }
	
	@Route
	public void handleLogin() {
		// TODO: add before filter
		Spark.post("/login", (request, response) -> {
			// TODO user login dto class
			String usernameOrEmail = StringEscapeUtils.escapeHtml4(request.queryParams("usernameOrEmail"));
			String password = StringEscapeUtils.escapeHtml4(request.queryParams("password"));
			User user = null;
			if(usernameOrEmail.contains("@")) {
				user = userService.findByEmail(usernameOrEmail);
			} else {
				user = userService.findByUsername(usernameOrEmail);
			}
			
			if(user != null && authService.isPasswordCorrect(user.getPassword(), password)) {
				AuthenticationToken token = userService.startSession(user);	
				response.cookie("session", token.getToken());
			}
			response.redirect("/");
			return null;
		});
	}
}
