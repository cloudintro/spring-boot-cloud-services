package com.cloudcode.springcloud.service.impl;

import com.cloudcode.springcloud.dao.AppJdbcRepo;
import com.cloudcode.springcloud.exception.AppCustomException;
import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;
import com.cloudcode.springcloud.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AppServiceImpl implements AppService {

    private static final String SUCCESS = "SUCCESS";
    private static final String DATA_EXISTS_WITH_KEY = "Data already exists with key: ";
    private static final String NO_DATA_EXISTS_WITH_KEY = "Data does not exists with key: ";

    @Autowired
    private AppJdbcRepo appRepo;
    // private AppJpaRepo appRepo;
    // private AppDataJpaRepo appRepo;

    @Override
    public List<Product> getProducts() {
        return appRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return appRepo.findById(id).orElseThrow(() -> new AppCustomException(NO_DATA_EXISTS_WITH_KEY + id));
    }

    @Override
    public Product saveProduct(ProductRequest request, boolean add) {
        if (add && Objects.nonNull(request.getId()) && appRepo.existsById(request.getId())) {
            throw new AppCustomException(DATA_EXISTS_WITH_KEY + request.getId());
        }
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return appRepo.save(product);
    }

    @Override
    public String deleteProductById(Long id) {
        if (appRepo.existsById(id)) {
            appRepo.deleteById(id);
            return SUCCESS;
        }
        return NO_DATA_EXISTS_WITH_KEY + id;
    }
}
