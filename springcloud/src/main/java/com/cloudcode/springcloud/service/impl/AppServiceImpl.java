package com.cloudcode.springcloud.service.impl;

import com.cloudcode.springcloud.exception.AppCustomException;
import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;
import com.cloudcode.springcloud.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class AppServiceImpl implements AppService {

    private static final String SUCCESS = "SUCCESS";
    private static final String DATA_EXISTS_WITH_KEY = "Data already exists with key: ";
    private static final String NO_DATA_FOUND_WITH_KEY = "No data found with key: ";

    private int seq = 0;

    @Autowired
    // private com.cloudcode.springcloud.dao.AppJdbcRepo appRepo;
    // private com.cloudcode.springcloud.dao.AppJpaRepo appRepo;
    private com.cloudcode.springcloud.dao.AppDataJpaRepo appRepo;

    @Override
    public List<Product> getProducts() {
        return appRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return appRepo.findById(id).orElseThrow(() -> new AppCustomException(NO_DATA_FOUND_WITH_KEY + id));
    }

    @Override
    public Product saveProduct(ProductRequest request, boolean add) {
        Product product = new Product();
        if (Objects.nonNull(request.getId())) {
            boolean exists = appRepo.existsById(request.getId());
            if (add && exists) {
                throw new AppCustomException(DATA_EXISTS_WITH_KEY + request.getId());
            } else if (!add && !exists) {
                throw new AppCustomException(NO_DATA_FOUND_WITH_KEY + request.getId());
            }
            product.setId(request.getId());
        } else {
            product.setId(Instant.now().toEpochMilli() + (++seq));
        }


        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return appRepo.save(product);
    }

    @Override
    public String deleteProductById(Long id) {
        if (!appRepo.existsById(id)) {
            throw new AppCustomException(NO_DATA_FOUND_WITH_KEY + id);
        }
        appRepo.deleteById(id);
        return SUCCESS;
    }
}
