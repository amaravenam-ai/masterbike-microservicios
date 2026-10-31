package com.masterbikes.servicio_tecnico.controllers;

import com.masterbikes.servicio_tecnico.dtos.request.OrdenServicioRequest;
import com.masterbikes.servicio_tecnico.dtos.response.OrdenServicioResponse;
import com.masterbikes.servicio_tecnico.services.OrdenServicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ordenes-servicio")
public class OrdenServicioController {

    private final OrdenServicioService ordenServicioService;

    public OrdenServicioController(OrdenServicioService ordenServicioService) {
        this.ordenServicioService = ordenServicioService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenServicioResponse>> listar() {
        return ResponseEntity.ok(ordenServicioService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenServicioService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<OrdenServicioResponse>> obtenerPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(ordenServicioService.obtenerPorCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<OrdenServicioResponse> guardar(@Valid @RequestBody OrdenServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenServicioService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenServicioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OrdenServicioRequest request
    ) {
        return ResponseEntity.ok(ordenServicioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ordenServicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
