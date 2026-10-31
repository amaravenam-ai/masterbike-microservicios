package com.masterbikes.clientes.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "cliente")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    @NotBlank
    private String rut;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 150)
    @NotBlank
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 150)
    @NotBlank
    private String correo;
}
