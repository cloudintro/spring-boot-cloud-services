package com.cloudcode.springcloud.service;

import java.util.List;

import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;

public interface AppService {
    List<Product> getProducts();

    Product getProductById(Long id);

    Product saveProduct(ProductRequest product);

    Product updateProduct(ProductRequest product);

    String deleteProductById(Long id);
}
