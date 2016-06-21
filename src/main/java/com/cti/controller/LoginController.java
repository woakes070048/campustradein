package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.service.AuthenticationService;
import com.cti.service.UserService;
import spark.Spark;

import javax.inject.Inject;

/**
 * @author ifeify
 */
@Controller
public class LoginController extends AbstractController {
    @Inject
    private AuthenticationService authenticationService;

    @Route
    public void handleLogin() {
        Spark.post("/login", (request, response) -> {
            // get from header
            String username = null;
            String password = null;
            if(authenticationService.login(username, password)) {

            } else {

            }
            return null;
        });
    }
}
