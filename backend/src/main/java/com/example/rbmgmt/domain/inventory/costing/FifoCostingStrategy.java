package com.example.rbmgmt.domain.inventory.costing;

import com.example.rbmgmt.domain.inventory.InventoryLot;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class FifoCostingStrategy implements CostingStrategy {
    @Override
    public BigDecimal calculateIssueUnitCost(List<InventoryLot> availableLots, BigDecimal issueQuantity) {
        // Simplified: return the unitCost of the oldest lot
        return availableLots.stream()
            .sorted(Comparator.comparing(InventoryLot::getReceivedAt))
            .findFirst()
            .map(InventoryLot::getUnitCost)
            .orElse(BigDecimal.ZERO);
    }
}


