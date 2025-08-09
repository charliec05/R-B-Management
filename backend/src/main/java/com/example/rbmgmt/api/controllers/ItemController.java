package com.example.rbmgmt.api.controllers;

import com.example.rbmgmt.api.dto.ItemDto;
import com.example.rbmgmt.api.mapper.ItemMapper;
import com.example.rbmgmt.domain.inventory.Item;
import com.example.rbmgmt.infra.repos.ItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS','READONLY')")
    public Page<ItemDto> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findAll(pageable).map(itemMapper::toDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemDto> create(@Valid @RequestBody ItemDto dto) {
        Item saved = itemRepository.save(itemMapper.toEntity(dto));
        ItemDto body = itemMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/items/" + saved.getId())).body(body);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_OPS','READONLY')")
    public ResponseEntity<ItemDto> get(@PathVariable Long id) {
        return itemRepository.findById(id)
            .map(itemMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemDto> update(@PathVariable Long id, @Valid @RequestBody ItemDto dto) {
        return itemRepository.findById(id)
            .map(existing -> {
                Item entity = itemMapper.toEntity(dto);
                entity.setId(existing.getId());
                return ResponseEntity.ok(itemMapper.toDto(itemRepository.save(entity)));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) return ResponseEntity.notFound().build();
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


