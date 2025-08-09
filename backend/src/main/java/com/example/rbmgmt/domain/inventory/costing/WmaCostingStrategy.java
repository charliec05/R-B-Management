package com.example.rbmgmt.domain.inventory.costing;

import com.example.rbmgmt.domain.inventory.InventoryLot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class WmaCostingStrategy implements CostingStrategy {
    @Override
    public BigDecimal calculateIssueUnitCost(List<InventoryLot> availableLots, BigDecimal issueQuantity) {
        // Weighted moving average of available lots by quantity
        BigDecimal totalQty = availableLots.stream()
            .map(InventoryLot::getQuantity)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalQty.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        BigDecimal totalCost = availableLots.stream()
            .map(l -> l.getUnitCost().multiply(l.getQuantity()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalCost.divide(totalQty, 4, RoundingMode.HALF_UP);
    }
}


