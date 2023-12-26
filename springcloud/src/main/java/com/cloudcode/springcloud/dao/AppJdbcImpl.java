package com.cloudcode.springcloud.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.cloudcode.springcloud.model.Product;

@Repository
public class AppJdbcImpl implements AppJdbcDao {

    List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<Product> productOptional = products.stream().filter(product -> product.getId() == id).findFirst();
        if (productOptional.isPresent()) {
            products.remove(productOptional.get());
        }
    }

}
