package lost.canvas.micronaut_test.controller;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lost.canvas.micronaut_test.common.entity.Result;
import lost.canvas.micronaut_test.interceptor.ResultLocalized;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Slf4j
@ResultLocalized
@Validated
@Controller
public class PingController {

    @Post(value = "/ping/body")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Result<Pong> pingJson2(HttpRequest req,
                                  @Valid @Body Ping ping) {
//        int v = 1 / 0;
        Pong pong = new Pong(req.getRemoteAddress().getHostString(), ping.getGreet());
        return Result.ok(pong);
    }

    @Data
    @AllArgsConstructor
    public static class Pong {
        private String pingIp;
        private String greet;
    }

    @Introspected
    @Data
    public static class Ping {
        @NotBlank(message = "{greet.NotBlank}")
        private String greet;
        @Max(value = 100,message = "{age.Max}")
        @Min(value = 0,message = "{age.Min}")
        private Integer age;
    }
}
