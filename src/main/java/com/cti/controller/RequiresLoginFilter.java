package com.cti.controller;

import spark.Filter;
import spark.Request;
import spark.Response;

import static com.google.common.base.Preconditions.checkNotNull;
import static spark.Spark.halt;

/**
 * @author ifeify
 */
public class RequiresLoginFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {
        try {
            // note that when a cookie expires, the browser simply deletes it. Also,
            // maxAge and other are not sent with each subsequent request. Just name and value
            String loggedIn = request.cookie("logged_in");
            String session = request.cookie("user_session");
            String username = request.cookie("dotcom_user");
            checkNotNull(loggedIn);
            checkNotNull(session);
            checkNotNull(username);
            if(loggedIn.equalsIgnoreCase("false")) {
                response.redirect("/");
                response.removeCookie("user_session");
                response.removeCookie("logged_in");
                response.removeCookie("dotcom_user");
                halt();
            }

        } catch(NullPointerException e) {
            response.removeCookie("user_session");
            response.removeCookie("logged_in");
            response.removeCookie("dotcom_user");
            response.body("You better log in");
            response.redirect("/");
            halt();
        }
    }
}
