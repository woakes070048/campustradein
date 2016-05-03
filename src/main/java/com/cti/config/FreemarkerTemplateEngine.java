package com.cti.config;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.ModelAndView;
import spark.TemplateEngine;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by ifeify on 5/2/16.
 */
public class FreemarkerTemplateEngine extends TemplateEngine {
    @Inject
    @Singleton
    private Configuration configuration;

    @Inject
    private FreemarkerTemplateEngine(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String render(ModelAndView modelAndView) {
        try {
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate(modelAndView.getViewName());
            template.process(modelAndView.getModel(), writer);
            return writer.toString();
        } catch(IOException e) {
            throw new IllegalArgumentException(e);
        } catch(TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
