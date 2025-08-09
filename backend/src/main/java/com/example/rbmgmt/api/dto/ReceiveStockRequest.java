package com.example.rbmgmt.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiveStockRequest {
    @NotBlank
    private String sku;
    @NotBlank
    private String warehouseCode;
    private String lotNo;
    @NotNull
    @DecimalMin(value = "0.0001")
    private BigDecimal qty;
    @NotNull
    @DecimalMin(value = "0.0001")
    private BigDecimal unitCost;
}


