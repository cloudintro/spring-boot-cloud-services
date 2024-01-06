package com.cloudcode.springcloud.service;

import com.cloudcode.springcloud.client.ProductServiceProxy;
import com.cloudcode.springcloud.dao.AppDataJpaRepo;
import com.cloudcode.springcloud.exception.AppCustomException;
import com.cloudcode.springcloud.model.Factory;
import com.cloudcode.springcloud.model.FactoryResponse;
import com.cloudcode.springcloud.model.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    ProductServiceProxy productService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AppDataJpaRepo appRepo;

    @Value("${product-service.auth.token:}")
    private String authToken;

    @Override
    public Factory createFactory(String factoryName) {
        Factory factory = appRepo.findById(factoryName).orElse(new Factory(factoryName, null));
        return appRepo.save(factory);
    }

    @Override
    public FactoryResponse getFactoryProducts(String factoryName) {
        Factory factory = appRepo.findById(factoryName).orElseThrow(() -> new AppCustomException(
                messageSource.getMessage("error.data.does.not.exists", new Object[]{factoryName},
                        Locale.getDefault())));
        List<Product> products = getProducts(factory);
        return new FactoryResponse(factoryName, products);
    }

    private List<Product> getProducts(Factory factory) {
        log.info("getting products from product-service");
        try {
            return productService.getProducts(getHeaders()).getBody();
        } catch (Exception e) {
            log.warn("product-service is down, getting products from local factory", e);
            return getLocalProducts(factory);
        }
    }

    private List<Product> getLocalProducts(Factory factory) {
        if (CollectionUtils.isEmpty(factory.getProductNames())) {
            return Collections.emptyList();
        }
        log.warn("returning local products");
        List<Product> products = new ArrayList<>();
        factory.getProductNames().forEach(name -> products.add(new Product(name, 999999.99D)));
        return products;
    }

    @Override
    public FactoryResponse createFactoryProduct(String factoryName, Product request) {
        Optional<Factory> factoryOptional = appRepo.findById(factoryName);
        Factory factory = null;
        if (factoryOptional.isPresent()) {
            factory = factoryOptional.get();
            if (!CollectionUtils.isEmpty(factory.getProductNames())) {
                if (factory.getProductNames().contains(request.getName())) {
                    // throw exception when product already created by factory
                    throw new AppCustomException(
                            messageSource.getMessage("error.data.already.exists", new Object[]{request.getName()},
                                    Locale.ENGLISH));

                }
                // create product in factory if product is already not created
                factory.getProductNames().add(request.getName());
            } else {
                // create product in factory if product is already not created
                factory.setProductNames(Set.of(request.getName()));
            }
        } else {
            // build new factory and product
            factory = new Factory(factoryName, Set.of(request.getName()));
        }
        Product product = saveProduct(request);
        appRepo.save(factory); // save new product
        return new FactoryResponse(factoryName, product);
    }

    private Product saveProduct(Product request) {
        try {
            return productService.saveProduct(getHeaders(), request).getBody(); // save product in warehouse
        } catch (RuntimeException ex) {
            log.error("product-service is down");
            throw new AppCustomException("product-service is down", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.put("Authorization", "Basic " + authToken);
        return headers;
    }

}
