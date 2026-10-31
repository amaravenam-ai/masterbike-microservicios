package com.masterbikes.servicio_tecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MasterBikesServicioTecnicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterBikesServicioTecnicoApplication.class, args);
    }
}
