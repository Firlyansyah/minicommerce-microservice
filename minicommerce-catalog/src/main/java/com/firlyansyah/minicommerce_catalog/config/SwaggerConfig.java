package com.firlyansyah.minicommerce_catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI catalogApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini Commerce Catalog API")
                        .version("1.0")
                        .description("Catalog Service API Documentation"));
    }
}