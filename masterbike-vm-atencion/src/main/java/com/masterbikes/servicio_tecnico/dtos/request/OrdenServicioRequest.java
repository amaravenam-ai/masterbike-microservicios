package com.masterbikes.servicio_tecnico.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OrdenServicioRequest {

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;

    @NotNull(message = "La hora de ingreso es obligatoria")
    private LocalTime horaIngreso;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El costo debe tener hasta 8 enteros y 2 decimales")
    private BigDecimal costo;

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    private Long idCliente;

    @Size(max = 500, message = "El problema no puede superar los 500 caracteres")
    private String problema;
}
