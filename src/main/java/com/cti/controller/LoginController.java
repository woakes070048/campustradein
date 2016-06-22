package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.repository.SessionRepository;
import com.cti.service.AuthenticationService;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import spark.Spark;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

/**
 * @author ifeify
 */
@Controller
public class LoginController extends AbstractController {
    @Inject
    private AuthenticationService authenticationService;
    @Inject
    private SessionRepository sessionRepository;

    @Route
    public void handleLogin() {
        Spark.post("/login", (request, response) -> {
            // get from header
            String username = null;
            String password = null;
            if(authenticationService.login(username, password)) {
                String sessionID = sessionRepository.newSession(username);
                response.cookie(Cookies.USER_SESSION, sessionID, 604800); // expires in a week
                response.cookie(Cookies.USER_NAME, username, 604800);
                response.cookie(Cookies.LOGGED_IN, "yes", 604800);
                response.status(HttpStatus.SC_OK);

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.append("redirect", "/");
                return jsonResponse;
            } else {
                response.status(HttpStatus.SC_BAD_REQUEST);
                return null;
            }
        });
    }
}
