package lost.canvas.micronaut_test;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Micronaut-Test",
                version = "1.0",
                description = "Micronaut Test API",
                license = @License(name = "Apache 2.0", url = "https://lost.canvas"),
                contact = @Contact(url = "https://lost.canvas", name = "lost-canvas", email = "lost-canvas")
        )
)
public class Application {

    
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
