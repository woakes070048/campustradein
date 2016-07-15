package com.cti.controller;

import com.cti.config.FreemarkerTemplateEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;

/**
 * @author ifeify
 */
public abstract class AbstractController {
    protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Inject
    protected FreemarkerTemplateEngine templateEngine;

    @Inject
    @Named("default.email.sender")
    protected String sender;

    protected Gson gson = new GsonBuilder()
                                .setLenient()
                                .setPrettyPrinting()
                                .create();

    /**
     * Retrieve the JWT stored in the cookie (for a web app) or in the Authorization header
     * @param request HTTP request
     * @return a string containing a JWT access token
     */
    protected Optional<String> getAccessToken(Request request) {
        // TODO: implement
        return Optional.empty();
    }

}
