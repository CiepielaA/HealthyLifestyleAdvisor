package com.ciepiela.adrian.dao;

import com.ciepiela.adrian.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Long> {
    Product findByDescription (String description);
}
