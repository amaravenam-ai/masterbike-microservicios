package com.masterbikes.ventas.repositories;

import com.masterbikes.ventas.models.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<VentaModel, Long> {
}
