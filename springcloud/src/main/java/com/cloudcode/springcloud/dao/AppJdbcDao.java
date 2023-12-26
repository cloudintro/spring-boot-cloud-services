package com.cloudcode.springcloud.dao;

import java.util.List;
import java.util.Optional;

import com.cloudcode.springcloud.model.Product;

public interface AppJdbcDao {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    Product update(Product product);
    void deleteProductById(Long id);
}
