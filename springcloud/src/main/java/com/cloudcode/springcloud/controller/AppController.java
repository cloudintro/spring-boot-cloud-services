package com.cloudcode.springcloud.controller;

import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;
import com.cloudcode.springcloud.service.AppService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/v1")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts() {
        log.info("received get product request");
        return new ResponseEntity<>(appService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("received get product by id request");
        return new ResponseEntity<>(appService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/product")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductRequest request) {
        log.info("received add product request");
        return new ResponseEntity<>(appService.saveProduct(request, true), HttpStatus.CREATED);
    }

    @PutMapping(path = "/product")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductRequest request) {
        log.info("received update product request");
        return new ResponseEntity<>(appService.saveProduct(request, false), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> delProductById(@PathVariable Long id) {
        log.info("received delete product by id request");
        return new ResponseEntity<>(appService.deleteProductById(id), HttpStatus.OK);
    }

}
