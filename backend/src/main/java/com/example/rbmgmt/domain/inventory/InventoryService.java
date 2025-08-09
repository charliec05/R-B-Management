package com.example.rbmgmt.domain.inventory;

import com.example.rbmgmt.infra.repos.InventoryLotRepository;
import com.example.rbmgmt.infra.repos.ItemRepository;
import com.example.rbmgmt.infra.repos.WarehouseRepository;
import com.example.rbmgmt.common.events.StockEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryLotRepository lotRepository;
    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockEventPublisher stockEventPublisher;

    @Transactional
    public InventoryLot receiveStockBySkuAndWarehouseCode(String sku, String warehouseCode, String lotNo,
                                                          BigDecimal qty, BigDecimal unitCost, Instant receivedAt) {
        var item = itemRepository.findBySku(sku).orElseThrow();
        var warehouse = warehouseRepository.findByCode(warehouseCode).orElseThrow();
        InventoryLot lot = InventoryLot.builder()
            .item(item)
            .warehouse(warehouse)
            .lotNo(lotNo)
            .qtyOnHand(qty)
            .costBasis(unitCost)
            .receivedAt(receivedAt == null ? Instant.now() : receivedAt)
            .build();
        return lotRepository.save(lot);
    }

    public void publishReceivedEvent(String sku, String warehouseCode, String lotNo) {
        stockEventPublisher.publishReceived(sku, warehouseCode, lotNo);
    }
}


