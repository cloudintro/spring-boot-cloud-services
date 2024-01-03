package com.cloudcode.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
public class FactoryResponse {
    private String factoryName;
    private List<Product> products;

    public FactoryResponse(String factoryName, Product product) {
        this.factoryName = factoryName;
        this.products = List.of(product);
    }
}
