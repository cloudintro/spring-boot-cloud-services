package com.cloudcode.springcloud.client;

import com.cloudcode.springcloud.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "product-service", url = "http://localhost:8080/product-service")
public interface ProductServiceProxy {
    @GetMapping("/v1/product")
    ResponseEntity<List<Product>> getProducts(@RequestHeader Map<String, String> headerMap);

    @PostMapping(path = "/v1/product")
    ResponseEntity<Product> saveProduct(@RequestHeader Map<String, String> headerMap, @RequestBody Product request);
}
