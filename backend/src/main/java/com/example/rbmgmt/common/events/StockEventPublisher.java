package com.example.rbmgmt.common.events;

public interface StockEventPublisher {
    void publishReceived(String sku, String warehouseCode, String lotNo);
}


