package com.masterbikes.usuarios.config;

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
                        .title("Masterbikes - Servicio de Usuarios")
                        .version("1.0.0")
                        .description("API REST para la gestión de usuarios del sistema Masterbikes. Administra roles y accesos.")
                        .contact(new Contact()
                                .name("Equipo Masterbikes")
                                .email("contacto@example.invalid")));
    }
}
