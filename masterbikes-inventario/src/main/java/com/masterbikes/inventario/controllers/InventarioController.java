package com.masterbikes.inventario.controllers;

import com.masterbikes.inventario.models.InventarioModel;
import com.masterbikes.inventario.services.InventarioService;
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
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public List<InventarioModel> listar() {
        return inventarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioModel> obtenerPorId(@PathVariable Long id) {
        Optional<InventarioModel> inventario = inventarioService.obtenerPorId(id);
        return inventario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventarioModel> guardar(@Valid @RequestBody InventarioModel inventario) {
        return ResponseEntity.ok(inventarioService.guardar(inventario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
