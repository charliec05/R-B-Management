package com.example.rbmgmt.api.dto;

import com.example.rbmgmt.domain.inventory.CostMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    @NotBlank
    private String sku;
    @NotBlank
    private String name;
    @NotBlank
    private String unit;
    @NotNull
    private CostMethod costMethod;
}


