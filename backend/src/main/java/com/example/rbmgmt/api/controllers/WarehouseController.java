package com.example.rbmgmt.api.controllers;

import com.example.rbmgmt.api.dto.WarehouseDto;
import com.example.rbmgmt.domain.inventory.Warehouse;
import com.example.rbmgmt.infra.repos.WarehouseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseRepository warehouseRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS','READONLY')")
    public List<Warehouse> list() {
        return warehouseRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Warehouse> create(@Valid @RequestBody WarehouseDto dto) {
        Warehouse warehouse = Warehouse.builder().code(dto.getCode()).name(dto.getName()).location(dto.getLocation()).build();
        Warehouse saved = warehouseRepository.save(warehouse);
        return ResponseEntity.created(URI.create("/api/warehouses/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS','READONLY')")
    public ResponseEntity<Warehouse> get(@PathVariable Long id) {
        return warehouseRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Warehouse> update(@PathVariable Long id, @Valid @RequestBody WarehouseDto dto) {
        return warehouseRepository.findById(id)
            .map(existing -> {
                existing.setCode(dto.getCode());
                existing.setName(dto.getName());
                existing.setLocation(dto.getLocation());
                return ResponseEntity.ok(warehouseRepository.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!warehouseRepository.existsById(id)) return ResponseEntity.notFound().build();
        warehouseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


