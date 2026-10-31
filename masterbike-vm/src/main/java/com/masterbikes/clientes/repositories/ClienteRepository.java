package com.masterbikes.clientes.repositories;

import com.masterbikes.clientes.models.ClienteModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    Optional<ClienteModel> findByRut(String rut);
    boolean existsByRut(String rut);
}
