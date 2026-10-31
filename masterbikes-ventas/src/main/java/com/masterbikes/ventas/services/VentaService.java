package com.masterbikes.ventas.services;

import com.masterbikes.ventas.models.VentaModel;
import com.masterbikes.ventas.repositories.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<VentaModel> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<VentaModel> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public VentaModel guardar(VentaModel venta) {
        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
}
