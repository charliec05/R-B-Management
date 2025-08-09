package com.example.rbmgmt.api.mapper;

import com.example.rbmgmt.api.dto.ItemDto;
import com.example.rbmgmt.domain.inventory.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toDto(Item entity);
    Item toEntity(ItemDto dto);
}


