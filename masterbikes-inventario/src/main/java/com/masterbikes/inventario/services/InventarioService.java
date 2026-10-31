package com.masterbikes.inventario.services;

import com.masterbikes.inventario.models.InventarioModel;
import com.masterbikes.inventario.repositories.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<InventarioModel> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Optional<InventarioModel> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public InventarioModel guardar(InventarioModel inventario) {
        return inventarioRepository.save(inventario);
    }

    public void eliminar(Long id) {
        inventarioRepository.deleteById(id);
    }
}
