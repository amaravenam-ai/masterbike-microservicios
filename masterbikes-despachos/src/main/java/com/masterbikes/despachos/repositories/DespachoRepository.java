package com.masterbikes.despachos.repositories;

import com.masterbikes.despachos.models.DespachoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoRepository extends JpaRepository<DespachoModel, Long> {
}
