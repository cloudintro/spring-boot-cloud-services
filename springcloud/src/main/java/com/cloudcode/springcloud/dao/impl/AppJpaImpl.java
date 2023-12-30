package com.cloudcode.springcloud.dao.impl;

import com.cloudcode.springcloud.dao.AppJpaRepo;
import com.cloudcode.springcloud.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AppJpaImpl implements AppJpaRepo {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_SELECT_ALL = "select id, name, price from product";
    private static final String SQL_COUNT_BY_ID = "select count(1) from product where id=:id";

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery(SQL_SELECT_ALL, Product.class);
        return query.getResultList();
    }

    @Override
    public Optional<Product> findById(long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    public Product save(Product product) {
        return entityManager.merge(product);
    }

    @Override
    public void deleteById(long id) {
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
    }

    @Override
    public boolean existsById(long id) {
        TypedQuery<Long> query = entityManager.createQuery(SQL_COUNT_BY_ID, Long.class);
        long count = query.setParameter("id", id).getSingleResult();
        return count > 0;
    }

}
