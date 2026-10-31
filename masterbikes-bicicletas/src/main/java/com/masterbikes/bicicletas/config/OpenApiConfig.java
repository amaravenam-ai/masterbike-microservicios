package com.masterbikes.bicicletas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Masterbikes - Catálogo de Bicicletas")
                        .version("1.0.0")
                        .description("API REST para la gestión del catálogo de bicicletas. Administra disponibilidad, tipos y tarifas.")
                        .contact(new Contact()
                                .name("Equipo Masterbikes")
                                .email("contacto@example.invalid")));
    }
}
