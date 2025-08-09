package com.example.rbmgmt.api.controllers;

import com.example.rbmgmt.api.dto.ReceiveStockRequest;
import com.example.rbmgmt.domain.inventory.InventoryLot;
import com.example.rbmgmt.domain.inventory.InventoryService;
import com.example.rbmgmt.infra.repos.InventoryLotRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryLotRepository inventoryLotRepository;

    @PostMapping("/receive")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS')")
    public ResponseEntity<InventoryLot> receive(@Valid @RequestBody ReceiveStockRequest dto) {
        InventoryLot saved = inventoryService.receiveStockBySkuAndWarehouseCode(
            dto.getSku(), dto.getWarehouseCode(), dto.getLotNo(), dto.getQty(), dto.getUnitCost(), Instant.now()
        );
        inventoryService.publishReceivedEvent(dto.getSku(), dto.getWarehouseCode(), dto.getLotNo());
        return ResponseEntity.created(URI.create("/api/inventory/lots/" + saved.getId())).body(saved);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS','READONLY')")
    public List<InventoryLotRepository.StockRow> currentStock() {
        return inventoryLotRepository.findCurrentStock();
    }
}


