package com.ciepiela.adrian.controllers;


import com.ciepiela.adrian.dao.ProductRepository;
import com.ciepiela.adrian.exceptions.ProductNotFoundException;
import com.ciepiela.adrian.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
@EnableWebMvc
public class ProductController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Product product) {
        productRepository.save(product);
        LOGGER.info("Create product with id: {} and description: {}", product.getId(), product.getDescription());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable long productId) {
        findIfProductExist(productId);
        productRepository.delete(productId);
        LOGGER.info("Delete product with id {}", productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{productId}", method = RequestMethod.POST)
    public ResponseEntity update(@RequestBody Product updatedProduct, @PathVariable long productId) {
        findIfProductExist(productId);
        Product product = productRepository.getOne(productId);
        product.setDescription(updatedProduct.getDescription());
        product.setKcal(updatedProduct.getKcal());
        product.setProtein(updatedProduct.getProtein());
        product.setFat(updatedProduct.getFat());
        product.setCarbs(updatedProduct.getCarbs());
        productRepository.save(product);
        LOGGER.info("Create product with id: {} and description: {}", product.getId(), product.getDescription());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/findById/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> findById(@PathVariable long productId) {
        Optional<Product> product = productRepository.findByProductId(productId);
        if (product.isPresent()) {
            LOGGER.info("Found product with id: {} ", productId);
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    @RequestMapping(value = "/findByDescription/{description}", method = RequestMethod.GET)
    public ResponseEntity<Product> findByEmail(@PathVariable String description) {
        Optional<Product> product = productRepository.findByDescription(description);
        if (product.isPresent()) {
            LOGGER.info("Found product with description: {} ", description);
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            throw new ProductNotFoundException(description);
        }
    }

    private void findIfProductExist(long productId) {
        productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
