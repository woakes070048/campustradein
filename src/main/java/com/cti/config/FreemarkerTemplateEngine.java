package com.cti.config;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.ModelAndView;
import spark.TemplateEngine;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by ifeify on 5/2/16.
 */
public class FreemarkerTemplateEngine extends TemplateEngine {
    @Inject
    private FreemarkerConfig freemarkerConfig;

    @Inject
    private FreemarkerTemplateEngine(FreemarkerConfig freemarkerConfig) {
        super();
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public String render(ModelAndView modelAndView) {
        try {
            StringWriter writer = new StringWriter();
            Template template = freemarkerConfig.getConfig().getTemplate(modelAndView.getViewName());
            template.process(modelAndView.getModel(), writer);
            return writer.toString();
        } catch(IOException e) {
            throw new IllegalArgumentException(e);
        } catch(TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
