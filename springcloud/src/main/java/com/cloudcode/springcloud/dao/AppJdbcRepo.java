package com.cloudcode.springcloud.dao;

import com.cloudcode.springcloud.model.Product;

import java.util.List;
import java.util.Optional;

public interface AppJdbcRepo {
    List<Product> findAll();

    Optional<Product> findById(long id);

    Product save(Product product);

    void deleteById(long id);

    boolean existsById(long id);
}
