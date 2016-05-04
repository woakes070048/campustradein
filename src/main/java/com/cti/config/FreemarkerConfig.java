package com.cti.config;

import com.google.inject.Singleton;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@Singleton
public class FreemarkerConfig {
    private Configuration cfg;

    public FreemarkerConfig() {
        if (cfg == null) {
            synchronized (this) {
                // double check
                if (cfg == null) {
                    cfg = new Configuration(Configuration.VERSION_2_3_23);
                    cfg.setDefaultEncoding("UTF-8");
                    cfg.setClassForTemplateLoading(FreemarkerConfig.class, "/html");
                    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
//			        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                    cfg.setLogTemplateExceptions(false);
                }
            }
        }
    }

    public Configuration getConfig() {
        return cfg;
    }
}
