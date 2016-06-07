package com.cti.controller;

import com.cti.config.FreemarkerTemplateEngine;
import com.google.gson.Gson;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by ifeify on 5/2/16.
 */
public abstract class AbstractController {
    @Inject
    protected Validator validator;

    @Inject
    protected FreemarkerTemplateEngine templateEngine;

    @Inject
    protected Gson gson = new Gson();

}
