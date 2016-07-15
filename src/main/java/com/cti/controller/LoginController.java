package com.cti.controller;

import com.cti.common.annotation.Controller;
import com.cti.common.annotation.Route;
import com.cti.common.auth.Credential;
import com.cti.common.exception.AuthenticationException;
import com.cti.repository.SessionRepository;
import com.cti.service.AuthenticationService;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.inject.Inject;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author ifeify
 */
@Controller
public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Inject
    private AuthenticationService authenticationService;

    @Route
    public void handleLogin() {
        Spark.post("/login", "application/json", (request, response) -> {
            try {
                JsonObject jsonResponse = new JsonObject();
                final String authorization = request.headers("Authorization");
                if (authorization != null && authorization.startsWith("Basic")) {
                    // Authorization: Basic base64Credentials
                    String base64Credentials = authorization.substring("Basic".length()).trim();
                    String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
                    final String[] values = credentials.split(":");

                    String username = values[0];
                    String password = values[1];
                    Credential credential = authenticationService.authenticate(username, password);
                    String accessToken = AuthenticationService.generateAuthToken(username);

                    response.cookie(Cookies.ACCESS_TOKEN, accessToken);
                    response.cookie(Cookies.USER_NAME, credential.getUsername());
                    response.cookie(Cookies.EMAIL, credential.getEmail());
                    response.header("Authorization", "Bearer " + accessToken);

                    jsonResponse.addProperty("result", "ok");
                    jsonResponse.addProperty("redirect", "/");
                    return jsonResponse;
                } else {
                    jsonResponse.addProperty("result", "error");
                    return jsonResponse;
                }
            } catch(AuthenticationException e) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("result", "error");
                jsonObject.addProperty("error", "Username or Email or Password is not recognized");
                return jsonObject;
            }
        }, gson::toJson);
    }
}
