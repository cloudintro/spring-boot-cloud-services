package com.cloudcode.springcloud.service;

import com.cloudcode.springcloud.model.FactoryResponse;
import com.cloudcode.springcloud.model.Product;

public interface AppService {
    FactoryResponse getFactoryProducts(String factoryName);

    FactoryResponse createFactoryProduct(String factoryName, Product request);
}
