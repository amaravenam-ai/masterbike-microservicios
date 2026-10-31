package com.masterbikes.despachos.services;

import com.masterbikes.despachos.models.DespachoModel;
import com.masterbikes.despachos.repositories.DespachoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DespachoService {

    private final DespachoRepository despachoRepository;

    public DespachoService(DespachoRepository despachoRepository) {
        this.despachoRepository = despachoRepository;
    }

    public List<DespachoModel> obtenerTodos() {
        return despachoRepository.findAll();
    }

    public Optional<DespachoModel> obtenerPorId(Long id) {
        return despachoRepository.findById(id);
    }

    public DespachoModel guardar(DespachoModel despacho) {
        return despachoRepository.save(despacho);
    }

    public void eliminar(Long id) {
        despachoRepository.deleteById(id);
    }
}
