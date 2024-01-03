package com.cloudcode.springcloud.service;

import com.cloudcode.springcloud.client.ProductServiceProxy;
import com.cloudcode.springcloud.dao.AppDataJpaRepo;
import com.cloudcode.springcloud.model.FactoryResponse;
import com.cloudcode.springcloud.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    ProductServiceProxy productService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AppDataJpaRepo appRepo;

    @Override
    public FactoryResponse getFactoryProducts(String factoryName) {
        List<Product> products = productService.getProducts().getBody();
        return new FactoryResponse(factoryName, products);
    }

    @Override
    public FactoryResponse createFactoryProduct(String factoryName, Product request) {
        Product product = productService.saveProduct(request).getBody();
        return new FactoryResponse(factoryName, product);
    }
}
