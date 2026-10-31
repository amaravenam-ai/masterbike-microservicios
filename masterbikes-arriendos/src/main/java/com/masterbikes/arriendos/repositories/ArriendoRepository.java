package com.masterbikes.arriendos.repositories;

import com.masterbikes.arriendos.models.ArriendoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArriendoRepository extends JpaRepository<ArriendoModel, Long> {
}
