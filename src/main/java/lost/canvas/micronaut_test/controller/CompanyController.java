package lost.canvas.micronaut_test.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import lost.canvas.micronaut_test.interceptor.ResultLocalized;

@ResultLocalized
@Validated
@Controller("/company")
public class CompanyController {
}
