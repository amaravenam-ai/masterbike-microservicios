package com.masterbikes.inventario.models;

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
@Table(name = "inventario")
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    @NotBlank
    private String nombreProducto;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String categoria;

    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer stock;

    @Column(name = "stock_minimo", nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer stockMinimo;
}
