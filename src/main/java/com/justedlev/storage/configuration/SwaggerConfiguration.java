package com.justedlev.storage.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Justedlev Services API")
                        .version("0.0.1")
                        .description("Justedlev Services API Configuration")
                        .termsOfService("swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("springdoc.org")));
    }
}

