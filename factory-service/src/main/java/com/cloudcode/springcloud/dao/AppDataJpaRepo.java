package com.cloudcode.springcloud.dao;

import com.cloudcode.springcloud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDataJpaRepo extends JpaRepository<Product, Long> {

}