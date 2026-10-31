package com.masterbikes.inventario.repositories;

import com.masterbikes.inventario.models.InventarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioModel, Long> {
}
