package com.masterbikes.despachos.controllers;

import com.masterbikes.despachos.models.DespachoModel;
import com.masterbikes.despachos.services.DespachoService;
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
@RequestMapping("/api/v1/despachos")
public class DespachoController {

    private final DespachoService despachoService;

    public DespachoController(DespachoService despachoService) {
        this.despachoService = despachoService;
    }

    @GetMapping
    public List<DespachoModel> listar() {
        return despachoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoModel> obtenerPorId(@PathVariable Long id) {
        Optional<DespachoModel> despacho = despachoService.obtenerPorId(id);
        return despacho.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DespachoModel> guardar(@Valid @RequestBody DespachoModel despacho) {
        return ResponseEntity.ok(despachoService.guardar(despacho));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        despachoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
