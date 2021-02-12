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
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


@Factory
public class I18nConfig {

    @Primary
    @Bean
    public MessageSource messageSource(RequestLocaleResolver requestLocaleResolver) {
        String messageBaseName = "i18n.messages";
        ResourceBundleMessageSource messageSource = new RequestLocaleAwareResourceBundleMessageSource(messageBaseName, requestLocaleResolver);
        return messageSource;
    }

    @Slf4j
    public static class RequestLocaleAwareResourceBundleMessageSource extends ResourceBundleMessageSource {

        private final RequestLocaleResolver requestLocaleResolver;

        public RequestLocaleAwareResourceBundleMessageSource(@NonNull String baseName, RequestLocaleResolver requestLocaleResolver) {
            super(baseName);
            this.requestLocaleResolver = requestLocaleResolver;
        }

        @NonNull
        @Override
        public Optional<String> getRawMessage(@NonNull String code, @NonNull MessageContext context) {
            ArgumentUtils.requireNonNull("code", code);
            ArgumentUtils.requireNonNull("context", context);

            //1. Accept-Language
            //2. micronaut.server.locale-resolution.default-locale
            //3. context.getLocale()
            Locale locale = ServerRequestContext.currentRequest().map(requestLocaleResolver::resolveOrDefault)
                    .orElse(context.getLocale());

            if (log.isDebugEnabled()) {
                log.debug("getRawMessage code[{}], resolve locale[{}]", code, locale);
            }

            if (!Objects.equals(context.getLocale(), locale)) {
                context = MessageContext.of(locale, context.getVariables());
            }
            return super.getRawMessage(code, context);
        }

    }
}
