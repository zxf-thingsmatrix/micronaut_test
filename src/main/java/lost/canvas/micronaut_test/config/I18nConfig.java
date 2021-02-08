package lost.canvas.micronaut_test.config;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import io.micronaut.core.util.ArgumentUtils;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.server.util.locale.RequestLocaleResolver;

import java.util.Locale;

@Factory
public class I18nConfig {

    private final String messageBaseName = "i18n.messages";

    @Primary
    @Bean
    public MessageSource messageSource(RequestLocaleResolver requestLocaleResolver) {
        ResourceBundleMessageSource messageSource = new RequestLocaleAwareResourceBundleMessageSource(messageBaseName, requestLocaleResolver);
        return messageSource;
    }

    public static class RequestLocaleAwareResourceBundleMessageSource extends ResourceBundleMessageSource {

        private final RequestLocaleResolver requestLocaleResolver;

        public RequestLocaleAwareResourceBundleMessageSource(@NonNull String baseName, RequestLocaleResolver requestLocaleResolver) {
            super(baseName);
            this.requestLocaleResolver = requestLocaleResolver;
        }

        @NonNull
        @Override
        public String interpolate(@NonNull String template, @NonNull MessageContext context) {

            ArgumentUtils.requireNonNull("template", template);
            ArgumentUtils.requireNonNull("context", context);

            Locale locale = ServerRequestContext.currentRequest().map(requestLocaleResolver::resolveOrDefault)
                    .orElse(context.getLocale());
            context = MessageContext.of(locale, context.getVariables());
            return super.interpolate(template, context);
        }

    }
}
