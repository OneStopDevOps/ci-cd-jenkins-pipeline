package com.jt.inventory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InventoryResource.class)
class InventoryResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private InventoryResource inventoryResource;

    public static List<Product> testProductsList;

    public static final String INVENTORY_URL = "/api/v1/inventory";

    @BeforeAll
    public static void setUp() {
        testProductsList = Stream.of(
                Product.builder().productCode("112").productName("Introduction to DevOps").quantity(1).build(),
                Product.builder().productCode("234").productName("Docker In Action").quantity(5).build())
                .collect(Collectors.toList());
    }

    @Test
    public void whenGetInventory_returnListOfProducts() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(INVENTORY_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andDo(print());
    }
}