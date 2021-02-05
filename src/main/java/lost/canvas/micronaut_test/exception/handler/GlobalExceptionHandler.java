package lost.canvas.micronaut_test.exception.handler;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;
import lost.canvas.micronaut_test.common.entity.ResultCode;

import javax.inject.Singleton;

@Slf4j
@Produces(value = MediaType.APPLICATION_JSON)
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<Throwable, Result<Void>> {

    @Error(global = true)
    @Override
    public Result<Void> handle(HttpRequest request, Throwable exception) {

        /**
         * 只能拦截到进入到 controller 方法块后的 Throwable!!!
         */
        log.error("========================global-exception========================", exception);
        return Result.fail(ResultCode.FAIL);
    }
}