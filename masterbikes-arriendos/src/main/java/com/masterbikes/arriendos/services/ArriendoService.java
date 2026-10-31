package com.masterbikes.arriendos.services;

import com.masterbikes.arriendos.models.ArriendoModel;
import com.masterbikes.arriendos.repositories.ArriendoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArriendoService {

    private final ArriendoRepository arriendoRepository;

    public ArriendoService(ArriendoRepository arriendoRepository) {
        this.arriendoRepository = arriendoRepository;
    }

    public List<ArriendoModel> obtenerTodos() {
        return arriendoRepository.findAll();
    }

    public Optional<ArriendoModel> obtenerPorId(Long id) {
        return arriendoRepository.findById(id);
    }

    public ArriendoModel guardar(ArriendoModel arriendo) {
        return arriendoRepository.save(arriendo);
    }

    public void eliminar(Long id) {
        arriendoRepository.deleteById(id);
    }

    public boolean estadoArriendo(Long id) {
        Optional<ArriendoModel> arriendoOpt = arriendoRepository.findById(id);
        if (arriendoOpt.isPresent()) {
            ArriendoModel arriendo = arriendoOpt.get();
            return "activo".equalsIgnoreCase(arriendo.getEstadoArriendo());
        }
        return false;
    }
}
