package com.masterbikes.servicio_tecnico.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "orden_servicio")
public class OrdenServicioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_ingreso", nullable = false)
    @NotNull
    private LocalDate fechaIngreso;

    @Column(name = "hora_ingreso", nullable = false)
    @NotNull
    private LocalTime horaIngreso;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal costo;

    @Column(name = "id_cliente", nullable = false)
    @NotNull
    @PositiveOrZero
    private Long idCliente;

    @Column(length = 500)
    private String problema;
}
