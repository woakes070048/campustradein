package com.cti.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfig {
    private static FreemarkerConfig instance;
    private Configuration cfg;

    private FreemarkerConfig() {}

    public static FreemarkerConfig getInstance() {
        if(instance == null) {
            synchronized(FreemarkerConfig.class) {
                if(instance == null) {
                    instance = new FreemarkerConfig();
                    instance.cfg = new Configuration(Configuration.VERSION_2_3_23);
                    instance.cfg.setDefaultEncoding("UTF-8");
                    instance.cfg.setClassForTemplateLoading(FreemarkerConfig.class, "/html");
                    instance.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
//			        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                    instance.cfg.setLogTemplateExceptions(false);
                }
            }
        }
        return instance;
    }

    public Configuration getConfig() {
        return cfg;
    }
}
