package com.ciepiela.adrian.dao;

import com.ciepiela.adrian.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId (long productId);

    Optional<Product> findByDescription (String description);
}
