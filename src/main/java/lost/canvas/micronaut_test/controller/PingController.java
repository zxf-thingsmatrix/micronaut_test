package lost.canvas.micronaut_test.controller;

import io.micronaut.context.MessageSource;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@Controller
public class PingController {

    @Inject
    private MessageSource messageSource;


    @Get(value = "/ping/plain")
    @Produces(value = MediaType.TEXT_PLAIN)
    public String pingPlain() {
        return "PONG !!!";
    }

    @Get(value = "/ping/json")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Pong pingJson(HttpRequest req) {
        return new Pong(req.getRemoteAddress().getHostString(), "PONG !!!");
    }

    @Post(value = "/ping/body")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Pong pingJson2(HttpRequest req,
                          @Valid @Body Ping ping) {
        int v = 1 / 0;
//        log.info("[{}] ping greet: {}", req.getRemoteAddress().getHostString(), ping.getGreet());
        return new Pong(req.getRemoteAddress().getHostString(), ping.getGreet());
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
    }
}
