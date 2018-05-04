package com.ciepiela.adrian.dao;

import com.ciepiela.adrian.model.FrontEndProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrontEndProductRepository extends JpaRepository<FrontEndProduct, Long> {
}
