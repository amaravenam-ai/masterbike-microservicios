package com.masterbikes.ventas.controllers;

import com.masterbikes.ventas.models.VentaModel;
import com.masterbikes.ventas.services.VentaService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<VentaModel> listar() {
        return ventaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaModel> obtenerPorId(@PathVariable Long id) {
        Optional<VentaModel> venta = ventaService.obtenerPorId(id);
        return venta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VentaModel> guardar(@Valid @RequestBody VentaModel venta) {
        return ResponseEntity.ok(ventaService.guardar(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
