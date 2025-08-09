package com.example.rbmgmt.common.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"default","dev"})
@Slf4j
public class InMemoryStockEventPublisher implements StockEventPublisher {
    @Override
    public void publishReceived(String sku, String warehouseCode, String lotNo) {
        log.info("Stock received event: sku={}, warehouseCode={}, lotNo={}", sku, warehouseCode, lotNo);
    }
}


