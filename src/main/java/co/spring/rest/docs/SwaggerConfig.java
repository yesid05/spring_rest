package co.spring.rest.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi(){
        return new OpenAPI().info(new Info().title("API Docs").description("Description").version("1.0.0"));
    }

}
