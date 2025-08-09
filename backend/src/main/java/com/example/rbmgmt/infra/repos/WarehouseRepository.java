package com.example.rbmgmt.infra.repos;

import com.example.rbmgmt.domain.inventory.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByCode(String code);
}


