package com.jt.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason Tao on 3/7/2021
 */
@RestController
@RequestMapping("/api/v1")
public class InventoryResource {

    public InventoryResource() {}

    @GetMapping("/inventory")
    public ResponseEntity<List<Product>> getInventory()
    {
        return ResponseEntity.ok().body(Stream.of(Product.builder().productCode("112").productName("Introduction to DevOps").quantity(1).build(),
                Product.builder().productCode("234").productName("Docker In Action").quantity(5).build()).collect(Collectors.toList()));
    }

}
