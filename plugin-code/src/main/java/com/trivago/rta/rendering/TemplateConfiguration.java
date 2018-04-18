package com.trivago.rta.rendering;

import com.trivago.rta.constants.PluginSettings;
import com.trivago.rta.exceptions.CluecumberPluginException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.inject.Singleton;

@Singleton
public class TemplateConfiguration {
    private Configuration cfg;

    void init(final Class rootClass, final String basePath) {
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(rootClass, basePath);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    Template getTemplate(final String templateName) throws CluecumberPluginException {
        Template template = null;
        try {
            template = cfg.getTemplate(templateName + PluginSettings.TEMPLATE_FILE_EXTENSION);
        } catch (Exception e) {
            throw new CluecumberPluginException("Template '" + templateName + "' was not found or not parsable: " +
                    e.getMessage());
        }
        return template;
    }
}
