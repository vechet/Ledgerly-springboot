package com.vechetchuo.Ledgerly.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define Bearer Token Security Scheme
        SecurityScheme bearerScheme = new SecurityScheme()
                .description("Standard Authorization header using the Bearer scheme ('bearer {token}')")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .type(SecurityScheme.Type.APIKEY)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Add the security requirement globally
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("oauth2");

        return new OpenAPI()
                .info(new Info()
                        .title("Ledgerly")
                        .version("1.0"))
                .addSecurityItem(securityRequirement)
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("oauth2", bearerScheme));
    }
}