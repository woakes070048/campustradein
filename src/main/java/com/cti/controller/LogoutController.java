package com.cti.controller;

import com.cti.common.annotation.Controller;
import com.cti.common.annotation.Route;
import com.cti.service.AuthenticationService;
import spark.Spark;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author ifeify
 */
@Controller
public class LogoutController extends AbstractController {
    @Inject
    private AuthenticationService authenticationService;

    @Route
    public void handleLogout() {
        Spark.before("/logout", new RequiresAuthenticationFilter());

        Spark.post("/logout", (request, response) -> {
            Optional<String> result = Optional.ofNullable(request.cookie(Cookies.USER_NAME));
            if(result.isPresent()) {
                String username = result.get();
                authenticationService.logout(username);
            }
            response.removeCookie(Cookies.USER_NAME);
            response.removeCookie(Cookies.USER_SESSION);
            response.removeCookie(Cookies.LOGGED_IN);
            response.redirect("/");
            return null;
        });
    }
}
