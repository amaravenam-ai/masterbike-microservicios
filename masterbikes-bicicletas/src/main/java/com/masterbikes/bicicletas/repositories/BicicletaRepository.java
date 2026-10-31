package com.masterbikes.bicicletas.repositories;

import com.masterbikes.bicicletas.models.BicicletaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicicletaRepository extends JpaRepository<BicicletaModel, Long> {
}
