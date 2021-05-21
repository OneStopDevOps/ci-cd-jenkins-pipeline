package com.jt.inventory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jason Tao on 3/7/2021
 */
@Builder
@Setter
@Getter
public class Product {

    private String productCode;
    private String productName;
    private Integer quantity;

    public Product(String productCode, String productName, Integer quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
    }
}
