package com.masterbikes.servicio_tecnico.dtos.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteResponse {

    private Long id;
    private String rut;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String correo;
}
