package com.cloudcode.springcloud.service;

import com.cloudcode.springcloud.dao.AppDataJpaRepo;
import com.cloudcode.springcloud.exception.AppCustomException;
import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class AppServiceImpl implements AppService {

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private MessageSource messageSource;

    private int seq = 0;

    @Autowired
    private AppDataJpaRepo appRepo;

    @Override
    public List<Product> getProducts() {
        return appRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return appRepo.findById(id).orElseThrow(() -> new AppCustomException(
                messageSource.getMessage("error.data.does.not.exists", new Object[] { id }, Locale.getDefault())));
    }

    @Override
    public Product saveProduct(ProductRequest request, boolean add) {
        Product product = new Product();
        if (Objects.nonNull(request.getId())) {
            boolean exists = appRepo.existsById(request.getId());
            if (add && exists) {
                throw new AppCustomException(messageSource.getMessage("error.data.already.exists",
                        new Object[] { request.getId() }, Locale.getDefault()));
            } else if (!add && !exists) {
                throw new AppCustomException(messageSource.getMessage("error.data.does.not.exists",
                        new Object[] { request.getId() }, Locale.getDefault()));
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
            throw new AppCustomException(messageSource.getMessage("error.data.does.not.exists",
                    new Object[] { id }, Locale.getDefault()));
        }
        appRepo.deleteById(id);
        return SUCCESS;
    }
}
