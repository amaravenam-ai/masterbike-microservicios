package com.masterbikes.servicio_tecnico.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class OrdenServicioResponse {

    private Long id;
    private LocalDate fechaIngreso;
    private LocalTime horaIngreso;
    private BigDecimal costo;
    private Long idCliente;
    private String problema;
    private ClienteResponse cliente;
}
