package com.example.rbmgmt.infra.repos;

import com.example.rbmgmt.domain.inventory.InventoryLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryLotRepository extends JpaRepository<InventoryLot, Long> {

    interface StockRow {
        Long getItemId();
        String getSku();
        String getItemName();
        Long getWarehouseId();
        String getWarehouseCode();
        String getWarehouseName();
        java.math.BigDecimal getQtyOnHand();
    }

    @Query("select i.item.id as itemId, i.item.sku as sku, i.item.name as itemName, " +
           "i.warehouse.id as warehouseId, i.warehouse.code as warehouseCode, i.warehouse.name as warehouseName, " +
           "sum(i.qtyOnHand) as qtyOnHand " +
           "from InventoryLot i group by i.item.id, i.item.sku, i.item.name, i.warehouse.id, i.warehouse.code, i.warehouse.name")
    List<StockRow> findCurrentStock();
}


