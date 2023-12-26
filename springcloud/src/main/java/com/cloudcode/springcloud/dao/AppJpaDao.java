package com.cloudcode.springcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudcode.springcloud.model.Product;

@Repository
public interface AppJpaDao extends JpaRepository<Product, Long> {

}