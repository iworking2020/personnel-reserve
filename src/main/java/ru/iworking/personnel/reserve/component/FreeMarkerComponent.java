package ru.iworking.personnel.reserve.component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FreeMarkerComponent {

    private static final Logger logger = LogManager.getLogger(FreeMarkerComponent.class);

    private final Configuration configuration;

    public String createHtml(String fileTemplate, Map<String, Object> props) {
        try(Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate(fileTemplate);
            template.process(props, writer);
            return writer.toString();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } catch (TemplateException e) {
            logger.error(e);
        }
        throw new RuntimeException("we cannot parse html...");
    }

}
