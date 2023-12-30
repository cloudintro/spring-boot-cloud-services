package com.cloudcode.springcloud.dao.impl;

import com.cloudcode.springcloud.dao.AppJdbcRepo;
import com.cloudcode.springcloud.model.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AppJdbcImpl implements AppJdbcRepo {

    private JdbcTemplate jdbcTemplate;

    public AppJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_SELECT_ALL = "select * from product";
    private static final String SQL_SELECT_BY_ID = "select * from product where id=?";
    private static final String SQL_INSERT = "insert into product(id, name, price) values (?, ?, ?)";
    private static final String SQL_UPDATE = "update product set name=?, price=? where id=?";
    private static final String SQL_DELETE_BY_ID = "delete from product where id=?";
    private static final String SQL_COUNT_BY_ID = "select count(1) from product where id=?";

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Optional<Product> findById(long id) {
        Product product = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new BeanPropertyRowMapper<>(Product.class), id);
        return Optional.ofNullable(product);
    }

    @Override
    public Product save(Product product) {
        if (!existsById(product.getId())) {
            jdbcTemplate.update(SQL_INSERT, product.getId(), product.getName(), product.getPrice());
        } else {
            jdbcTemplate.update(SQL_UPDATE, product.getName(), product.getPrice(), product.getId());
        }
        return product;
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public boolean existsById(long id) {
        Integer count = jdbcTemplate.queryForObject(SQL_COUNT_BY_ID, Integer.class, id);
        return Objects.nonNull(count) && count > 0;
    }

}
