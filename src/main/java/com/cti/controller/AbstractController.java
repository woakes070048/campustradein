package com.cti.controller;

import com.cti.config.FreemarkerTemplateEngine;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Validation;
import javax.validation.Validator;

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

    protected Gson gson = new Gson();

}
