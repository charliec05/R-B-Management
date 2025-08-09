package com.example.rbmgmt.api.controllers;

import com.example.rbmgmt.domain.inventory.InventoryLot;
import com.example.rbmgmt.infra.repos.InventoryLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/lots")
@RequiredArgsConstructor
public class InventoryLotController {
    private final InventoryLotRepository lotRepository;

    @GetMapping
    public List<InventoryLot> list() { return lotRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryLot> get(@PathVariable Long id) {
        return lotRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!lotRepository.existsById(id)) return ResponseEntity.notFound().build();
        lotRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


