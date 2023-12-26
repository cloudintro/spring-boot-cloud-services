package com.cloudcode.springcloud.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcode.springcloud.exception.DataAlreadyExistsException;
import com.cloudcode.springcloud.exception.NoDataFoundException;
import com.cloudcode.springcloud.dao.AppJdbcDao;
import com.cloudcode.springcloud.dao.AppJpaDao;
import com.cloudcode.springcloud.model.Product;
import com.cloudcode.springcloud.model.ProductRequest;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppJpaDao appDao;

    @Override
    public List<Product> getProducts() {
        return appDao.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return appDao.findById(id).orElseThrow(() -> new NoDataFoundException("No data found"));
    }

    @Override
    public Product saveProduct(ProductRequest request) {
        if (appDao.findById(request.getId()).isPresent()) {
            throw new DataAlreadyExistsException("Data already exists");
        }
        Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .createOn(LocalDateTime.now())
                .build();
        return appDao.save(product);
    }

    @Override
    public Product updateProduct(ProductRequest request) {
        Product product = appDao.findById(request.getId()).orElseThrow(() -> new NoDataFoundException("No data found"));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setUpdatedOn(LocalDateTime.now());
        return appDao.save(product);
    }

    @Override
    public String deleteProductById(Long id) {
        if (appDao.existsById(id)) {
            appDao.deleteById(id);
        } else {
            throw new NoDataFoundException("No data found");
        }
        return "SUCCESS";
    }
}
