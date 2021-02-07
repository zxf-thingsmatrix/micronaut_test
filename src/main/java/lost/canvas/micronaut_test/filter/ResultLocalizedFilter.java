package lost.canvas.micronaut_test.filter;

import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.server.util.locale.RequestLocaleResolver;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * 对 Result 进行本地化处理
 */
@Slf4j
@Filter(value = Filter.MATCH_ALL_PATTERN)
public class ResultLocalizedFilter implements HttpServerFilter {

    @Inject
    private MessageSource messageSource;
    @Inject
    private RequestLocaleResolver requestLocaleResolver;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        return Flowable.fromPublisher(chain.proceed(request))
                .map(mutableHttpResponse -> {
                    if (log.isDebugEnabled()) {
                        log.debug("[ResultLocalizedFilter] doFilter,before response.status={} response.body={}", mutableHttpResponse.getStatus(), mutableHttpResponse.getBody());
                    }
                    mutableHttpResponse.getBody(Result.class).ifPresent(result -> {
                        Result localizedResult = localizeResult(request, result);
                        mutableHttpResponse.body(localizedResult);
                    });
                    if (log.isDebugEnabled()) {
                        log.debug("[ResultLocalizedFilter] doFilter,after response.status={} response.body={}", mutableHttpResponse.getStatus(), mutableHttpResponse.getBody());
                    }
                    return mutableHttpResponse;
                });
    }

    private <T> Result<T> localizeResult(HttpRequest<?> request, Result<T> result) {
        if (Objects.nonNull(result)) {
            //Accept-Language 读取 locale, 没有默认取 micronaut.server.locale-resolution.default-locale
            Locale locale = requestLocaleResolver.resolveOrDefault(request);
            if (log.isDebugEnabled()) {
                log.debug("[ResultLocalizedFilter] doFilter, locale={}", locale);
            }
            return result.localize((keyAndDefaultMessage, variables) -> {
                return localizeMessage(keyAndDefaultMessage[0], keyAndDefaultMessage[1], locale, variables);
            });
        }
        return null;
    }

    private String localizeMessage(String key, String defaultMessage, Locale locale, Map<String, Object> variables) {
        MessageSource.MessageContext context = MessageSource.MessageContext.of(locale, variables);
        return messageSource.getMessage(key, context, defaultMessage);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
