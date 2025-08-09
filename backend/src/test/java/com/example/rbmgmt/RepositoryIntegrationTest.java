package com.example.rbmgmt;

import com.example.rbmgmt.domain.inventory.CostMethod;
import com.example.rbmgmt.domain.inventory.Item;
import com.example.rbmgmt.infra.repos.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class RepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("rbmgmt")
        .withUsername("rbuser")
        .withPassword("rbpass");

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    ItemRepository itemRepository;

    @Test
    void savesItem() {
        Item item = Item.builder().sku("BRG-6001").name("Bearing 6001").unit("pcs").costMethod(CostMethod.FIFO).build();
        Item saved = itemRepository.save(item);
        assertThat(saved.getId()).isNotNull();
    }
}


