package com.cloudcode.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FactoryResponse {
    private String factory;
    private List<Product> products;

    public FactoryResponse(String factoryName, Product product) {
        this.factory = factoryName;
        this.products = List.of(product);
    }
}
