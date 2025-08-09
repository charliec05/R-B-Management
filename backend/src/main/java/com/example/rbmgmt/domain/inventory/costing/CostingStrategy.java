package com.example.rbmgmt.domain.inventory.costing;

import com.example.rbmgmt.domain.inventory.InventoryLot;

import java.math.BigDecimal;
import java.util.List;

public interface CostingStrategy {
    BigDecimal calculateIssueUnitCost(List<InventoryLot> availableLots, BigDecimal issueQuantity);
}


