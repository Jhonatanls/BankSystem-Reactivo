package com.training.reactive_bank_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema bancario con transacciones reactivas")
                        .version("1.0")
                        .description("API para auditar transacciones de un sistema bancario"));
    }

}
