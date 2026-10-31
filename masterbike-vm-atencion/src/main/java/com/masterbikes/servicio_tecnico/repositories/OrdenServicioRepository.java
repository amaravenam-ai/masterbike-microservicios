package com.masterbikes.servicio_tecnico.repositories;

import com.masterbikes.servicio_tecnico.models.OrdenServicioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenServicioRepository extends JpaRepository<OrdenServicioModel, Long> {

    List<OrdenServicioModel> findByIdCliente(Long idCliente);
}
