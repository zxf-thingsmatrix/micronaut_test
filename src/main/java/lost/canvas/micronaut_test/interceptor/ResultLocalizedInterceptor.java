package lost.canvas.micronaut_test.interceptor;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.MessageSource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Singleton
public class ResultLocalizedInterceptor implements MethodInterceptor<Object, Object> {

    @Inject
    private MessageSource messageSource;

    @Override
    public int getOrder() {
        return InterceptPhaseEx.result_localized.getPosition();
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        Object before = context.proceed();
        Object after = before;

        if (log.isDebugEnabled()) {
            log.debug("intercept, before result={}", before);
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
            log.debug("intercept, after result={}", after);
        }

        return after;
    }

    private Result<?> localizeResult(Result<?> result) {
        if (Objects.nonNull(result)) {
            return result.localize((keyAndDefaultMessage, variables) -> {
                return localizeMessage(keyAndDefaultMessage[0], keyAndDefaultMessage[1], variables);
            });
        }
        return null;
    }


    private String localizeMessage(String key, String defaultMessage, Map<String, Object> variables) {
        MessageSource.MessageContext context = MessageSource.MessageContext.of(variables);
        return messageSource.getMessage(key, context, defaultMessage);
    }
}
