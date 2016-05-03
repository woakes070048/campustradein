package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.config.FreemarkerTemplateEngine;
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
public class HomePageController extends UIController {
    @Inject
    private UserService userService;

    @Inject
    private FreemarkerTemplateEngine templateEngine;

    @Route
    public void handleRenderHomepage() {
        Spark.get("/", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            String sessionID = request.cookie("session");
            if(sessionID != null && !sessionID.isEmpty()) {
                User user = userService.findUserBySessionID(sessionID);
                model.put("username", user.getUsername());
                if(!user.isActivated()) {
                    model.put("needsActivation", "true");
                }
            }
            return templateEngine.render(new ModelAndView(model, "welcome.ftl"));
        });
    }
}
