package com.example.rbmgmt.api.mapper;

import com.example.rbmgmt.api.dto.ItemDto;
import com.example.rbmgmt.domain.inventory.Item;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-09T23:14:40+0800",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public ItemDto toDto(Item entity) {
        if ( entity == null ) {
            return null;
        }

        ItemDto itemDto = new ItemDto();

        itemDto.setCostMethod( entity.getCostMethod() );
        itemDto.setId( entity.getId() );
        itemDto.setName( entity.getName() );
        itemDto.setSku( entity.getSku() );
        itemDto.setUnit( entity.getUnit() );

        return itemDto;
    }

    @Override
    public Item toEntity(ItemDto dto) {
        if ( dto == null ) {
            return null;
        }

        Item.ItemBuilder item = Item.builder();

        item.costMethod( dto.getCostMethod() );
        item.id( dto.getId() );
        item.name( dto.getName() );
        item.sku( dto.getSku() );
        item.unit( dto.getUnit() );

        return item.build();
    }
}
