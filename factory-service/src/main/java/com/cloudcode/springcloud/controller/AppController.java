package com.cloudcode.springcloud.controller;

import com.cloudcode.springcloud.model.Factory;
import com.cloudcode.springcloud.model.FactoryResponse;
import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.service.AppService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/v1")
public class AppController {

    @Autowired
    private AppService appService;

    @PostMapping("/factory/{factoryName}")
    public ResponseEntity<Factory> createFactory(@PathVariable String factoryName) {
        log.info("received create factory request for : {}", factoryName);
        return new ResponseEntity<>(appService.createFactory(factoryName), HttpStatus.CREATED);
    }

    @GetMapping("/{factoryName}/products")
    public ResponseEntity<FactoryResponse> getProducts(@PathVariable String factoryName) {
        log.info("received get product request for factory: {}", factoryName);
        return new ResponseEntity<>(appService.getFactoryProducts(factoryName), HttpStatus.OK);
    }

    @PostMapping("/{factoryName}/product")
    public ResponseEntity<FactoryResponse> createProduct(@PathVariable String factoryName,
                                                         @RequestBody @Valid Product product) {
        log.info("received add product requestfor factory: {}, product: {}", factoryName, product);
        return new ResponseEntity<>(appService.createFactoryProduct(factoryName, product), HttpStatus.CREATED);
    }

}
