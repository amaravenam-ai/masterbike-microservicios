package com.masterbikes.arriendos.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "arriendo")
public class ArriendoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    @NotNull
    @PositiveOrZero
    private Long idCliente;

    @Column(name = "id_bicicleta", nullable = false)
    @NotNull
    @PositiveOrZero
    private Long idBicicleta;

    @Column(name = "fecha_inicio", nullable = false)
    @NotNull
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @NotNull
    private LocalDate fechaFin;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal costo;

    @Column(name = "estadoarriendo", nullable = false, length = 50)
    @NotBlank
    private String estadoArriendo;



}
