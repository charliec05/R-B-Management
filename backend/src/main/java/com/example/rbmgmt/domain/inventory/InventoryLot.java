package com.example.rbmgmt.domain.inventory;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "inventory_lots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "qty_on_hand", nullable = false, precision = 18, scale = 3)
    private BigDecimal qtyOnHand;

    @Column(name = "cost_basis", nullable = false, precision = 18, scale = 4)
    private BigDecimal costBasis;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;
}


