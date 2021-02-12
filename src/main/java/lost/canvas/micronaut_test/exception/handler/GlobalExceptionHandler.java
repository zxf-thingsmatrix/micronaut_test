package lost.canvas.micronaut_test.exception.handler;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;
import lost.canvas.micronaut_test.common.entity.ResultCode;
import lost.canvas.micronaut_test.common.exception.ServiceException;
import lost.canvas.micronaut_test.common.util.Utils;
import lost.canvas.micronaut_test.interceptor.ResultLocalized;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 泛型必须是 {@link HttpResponse} 类型，否则默认会 wrap 一个 http.status=500 的 {@link HttpResponse}
 * <p>
 * RoutingInBoundHandler#errorResultToResponse(java.lang.Object)
 */
@Slf4j
//替换 ConstraintExceptionHandler，使得 可以拦截ConstraintException
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
@Produces(value = MediaType.APPLICATION_JSON)
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Throwable, HttpResponse<Result<Void>>> {

    @ResultLocalized
    @Override
    public HttpResponse<Result<Void>> handle(HttpRequest request, Throwable exception) {

        log.error("========================handle-global-exception========================", exception);
        //拦截 validation 相关异常
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            Object[] messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                    .filter(Utils.empty::nonEmpty)
                    .collect(Collectors.toList()).toArray(new String[]{});
            Result<Void> result = Result.fail(ResultCode.validate_fail, Utils.message.convertVariables(messages));
            return HttpResponse.ok(result);
        }

        //拦截 ServiceException
        if (exception instanceof ServiceException) {
            ServiceException ex = (ServiceException) exception;
            Result<Void> result = Result.fail(ex.getResultCode(), ex.getVariables());
            return HttpResponse.ok(result);
        }

        Result<Void> result = Result.fail(ResultCode.unknown_exception);
        return HttpResponse.ok(result);
    }
}