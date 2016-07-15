package com.cti.controller;

import com.cti.common.annotation.Controller;
import com.cti.common.annotation.Route;
import org.apache.commons.lang3.RandomStringUtils;
import spark.ModelAndView;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ifeify
 */
@Controller
public class BookListingController extends AbstractController {

    @Route
    public void getBookListingForm() {
        Spark.before("/listbook", new RequiresAuthorization());

        Spark.get("/listbook", (request, response) -> {
            String csrfToken = RandomStringUtils.randomAlphanumeric(32);
            Map<String, String> model = new HashMap<>();
            model.put("csrf_token", csrfToken);
            // TODO: also include the csrf token in response header authorization
            return templateEngine.render(new ModelAndView(model, "wizard.ftl"));
        });
    }
    @Route
    public void handleImageUpload() {

    }
}
