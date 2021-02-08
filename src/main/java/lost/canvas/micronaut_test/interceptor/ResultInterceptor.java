package lost.canvas.micronaut_test.interceptor;

import io.micronaut.aop.InterceptPhase;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.server.util.locale.RequestLocaleResolver;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Singleton
public class ResultInterceptor implements MethodInterceptor<Object, Object> {

    @Inject
    private MessageSource messageSource;
    @Inject
    private RequestLocaleResolver requestLocaleResolver;

    @Override
    public int getOrder() {
        return InterceptPhaseEx.result.getPosition();
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        Object before = context.proceed();
        Object after = before;

        if (log.isDebugEnabled()) {
            log.debug("[ResultInterceptor] intercept, before result={}", before);
        }

        //本地化处理
        if (before instanceof Result) {
            after = Optional.ofNullable((Result) before).map(this::localizeResult).orElse((Result) before);
        }
        //本地化处理
        else if (before instanceof HttpResponse) {
            after = ((Optional<Result>) (HttpResponse.class.cast(before).getBody(Result.class)))
                    .map(this::localizeResult).map(HttpResponse::ok)
                    .orElse((MutableHttpResponse) before);
        }


        if (log.isDebugEnabled()) {
            log.debug("[ResultInterceptor] intercept, after result={}", after);
        }

        return after;
    }

    private Result<?> localizeResult(Result<?> result) {
        if (Objects.nonNull(result)) {
            Optional<HttpRequest<Object>> request = ServerRequestContext.currentRequest();
            if (!request.isPresent()) {
                log.error("[ResultInterceptor] intercept,can not get current HttpRequest from ServerRequestContext");
                return result;
            } else {
                //Accept-Language 读取 locale, 没有默认取 micronaut.server.locale-resolution.default-locale
                Locale locale = requestLocaleResolver.resolveOrDefault(request.get());
                if (log.isDebugEnabled()) {
                    log.debug("[ResultInterceptor] intercept, locale={}", locale);
                }
                return result.localize((keyAndDefaultMessage, variables) -> {
                    return localizeMessage(keyAndDefaultMessage[0], keyAndDefaultMessage[1], locale, variables);
                });
            }
        }
        return null;
    }

    private String localizeMessage(String key, String defaultMessage, Locale locale, Map<String, Object> variables) {
        MessageSource.MessageContext context = MessageSource.MessageContext.of(locale, variables);
        return messageSource.getMessage(key, context, defaultMessage);
    }
}
