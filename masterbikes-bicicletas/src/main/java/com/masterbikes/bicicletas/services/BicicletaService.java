package com.masterbikes.bicicletas.services;

import com.masterbikes.bicicletas.models.BicicletaModel;
import com.masterbikes.bicicletas.repositories.BicicletaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BicicletaService {

    private final BicicletaRepository bicicletaRepository;

    public BicicletaService(BicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    public List<BicicletaModel> obtenerTodas() {
        return bicicletaRepository.findAll();
    }

    public Optional<BicicletaModel> obtenerPorId(Long id) {
        return bicicletaRepository.findById(id);
    }

    public BicicletaModel guardar(BicicletaModel bicicleta) {
        return bicicletaRepository.save(bicicleta);
    }

    public void eliminar(Long id) {
        bicicletaRepository.deleteById(id);
    }
}
