package com.cloudcode.springcloud.service;

import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;

import java.util.List;

public interface AppService {
    List<Product> getProducts();

    Product getProductById(Long id);

    Product saveProduct(ProductRequest product, boolean add);

    String deleteProductById(Long id);
}
