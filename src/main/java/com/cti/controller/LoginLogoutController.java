package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.service.UserService;
import spark.Spark;

/**
 * Created by ifeify on 4/30/16.
 */
@Controller
public class LoginLogoutController {
    private UserService userService;

    public void setupEndpoints() {
        Spark.get("/logout", (request, response) -> {
            return null;
        });

        Spark.post("/login", (request, response) -> {
            return null;
        });
    }
}
