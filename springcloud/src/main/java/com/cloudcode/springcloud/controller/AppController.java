package com.cloudcode.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcode.springcloud.service.AppService;

import jakarta.validation.Valid;

import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return new ResponseEntity<>(appService.saveProduct(request), HttpStatus.CREATED);
    }

    @PutMapping(path = "/product")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductRequest request) {
        log.info("received update product request");
        return new ResponseEntity<>(appService.updateProduct(request), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> delProductById(@PathVariable Long id) {
        log.info("received delete product by id request");
        return new ResponseEntity<>(appService.deleteProductById(id), HttpStatus.OK);
    }

}
