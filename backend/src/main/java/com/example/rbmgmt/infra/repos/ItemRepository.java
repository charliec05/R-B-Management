package com.example.rbmgmt.infra.repos;

import com.example.rbmgmt.domain.inventory.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findBySku(String sku);
}


