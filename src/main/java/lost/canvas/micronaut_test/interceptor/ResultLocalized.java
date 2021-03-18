package lost.canvas.micronaut_test.interceptor;

import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Around
@Type(ResultLocalizedInterceptor.class)
public @interface ResultLocalized {
}
