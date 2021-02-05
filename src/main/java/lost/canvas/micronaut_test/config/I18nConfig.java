package lost.canvas.micronaut_test.config;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;

@Factory
public class I18nConfig {

    private final String messageBaseName = "i18n.messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(messageBaseName);
        return messageSource;
    }
}
