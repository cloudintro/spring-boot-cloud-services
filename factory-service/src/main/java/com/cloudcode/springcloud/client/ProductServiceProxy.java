package com.cloudcode.springcloud.client;

import com.cloudcode.springcloud.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8080/product-service")
public interface ProductServiceProxy {
    @GetMapping("/product")
    ResponseEntity<List<Product>> getProducts();

    @PostMapping(path = "/product")
    ResponseEntity<Product> saveProduct(@RequestBody Product request);
}
