package com.firlyansyah.minicommerce_order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI orderApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini Commerce Order API")
                        .version("1.0")
                        .description("Order Service API Documentation"));
    }
}