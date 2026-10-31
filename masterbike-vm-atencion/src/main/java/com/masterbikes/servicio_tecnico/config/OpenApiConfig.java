package com.masterbikes.servicio_tecnico.config;

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
                        .title("Masterbikes - Servicio Técnico")
                        .version("1.0.0")
                        .description("API REST para la gestión de órdenes de servicio técnico. Integra comunicación con el microservicio de clientes.")
                        .contact(new Contact()
                                .name("Equipo Masterbikes")
                                .email("contacto@example.invalid")));
    }
}
