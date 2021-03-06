package com.cti.controller;

import com.cti.common.annotation.Controller;
import com.cti.common.annotation.Route;
import spark.ModelAndView;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ifeify
 */
@Controller
public class HomePageController extends AbstractController {
    @Route
    public void getHomepage() {
        Spark.get("/", (request, response) -> {
            String username = request.cookie(Cookies.USER_NAME);
            String signupWasSuccessful = request.cookie(Cookies.SIGNUP_SUCCESS);
            String email = request.cookie(Cookies.EMAIL);

            Map<String, String> model = new HashMap<>();
            if(username != null && !username.isEmpty()) {
                model.put("username", username);
            }
            // TODO: not a clean approach but works for now
            // technical debt
            if(signupWasSuccessful != null && email != null) {
                // this cookie is set by the registration controller to indicate a successful signup
                model.put("signupWasSuccessful", "true");
                model.put("email", email);
                response.removeCookie(Cookies.SIGNUP_SUCCESS);
                response.removeCookie(Cookies.EMAIL);
            }
            return templateEngine.render(new ModelAndView(model, "home.ftl"));
        });
    }
}
