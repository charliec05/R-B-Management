package com.example.rbmgmt.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarehouseDto {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String location;
}


