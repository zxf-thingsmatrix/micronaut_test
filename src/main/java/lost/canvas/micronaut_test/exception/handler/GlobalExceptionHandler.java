package lost.canvas.micronaut_test.exception.handler;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;
import lost.canvas.micronaut_test.common.entity.ResultCode;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
//替换 ConstraintExceptionHandler，使得 可以拦截ConstraintException
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
@Produces(value = MediaType.APPLICATION_JSON)
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Throwable, Result<Void>> {

    @Inject
    private MessageSource messageSource;

    @Override
    public Result<Void> handle(HttpRequest request, Throwable exception) {

        Locale locale = (Locale) request.getLocale().orElse(Locale.getDefault());

        log.error("========================global-exception========================", exception);

        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            String message = ex.getConstraintViolations().iterator().next().getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("0", message);
            String localMessage = messageSource.getMessage(ResultCode.validate_fail.getMessageKey(), MessageSource.MessageContext.of(locale, map), ResultCode.validate_fail.getDefaultMessage());
            return new Result(ResultCode.validate_fail.getCode(), localMessage, null);
        }
        return Result.fail(ResultCode.fail);
    }
}