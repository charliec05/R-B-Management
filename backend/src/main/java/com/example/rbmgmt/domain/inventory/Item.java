package com.example.rbmgmt.domain.inventory;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "cost_method", nullable = false)
    private CostMethod costMethod;
}


