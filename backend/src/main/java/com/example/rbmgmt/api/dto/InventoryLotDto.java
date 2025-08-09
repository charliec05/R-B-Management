package com.example.rbmgmt.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class InventoryLotDto {
    private Long id;
    @NotNull
    private Long itemId;
    @NotNull
    private Long warehouseId;
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private BigDecimal unitCost;
    private Instant receivedAt;
}


