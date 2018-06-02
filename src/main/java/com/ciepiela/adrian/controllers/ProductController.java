package com.ciepiela.adrian.controllers;


import com.ciepiela.adrian.dao.ProductRepository;
import com.ciepiela.adrian.exceptions.ProductNotFoundException;
import com.ciepiela.adrian.model.FrontEndProduct;
import com.ciepiela.adrian.model.Product;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        LOGGER.info("Create product with id: {} and description: {}", product.getId(), product.getDescription());
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteById/{productId}", method = RequestMethod.GET)
    public ResponseEntity deleteById(@PathVariable long productId) {
        findIfProductExist(productId);
        productRepository.delete(productId);
        LOGGER.info("Delete product with id {}", productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteByDescription/{description}", method = RequestMethod.GET)
    public ResponseEntity deleteByDescription(@PathVariable String description) {
        Product existingProduct = findIfProductExist(description);
        productRepository.delete(existingProduct.getId());
        LOGGER.info("Delete product with id {}", existingProduct.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Product> update(@RequestBody Product updatedProduct) {
        Product existingProduct = findIfProductExist(updatedProduct.getDescription());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setKcal(updatedProduct.getKcal());
        existingProduct.setProteins(updatedProduct.getProteins());
        existingProduct.setFats(updatedProduct.getFats());
        existingProduct.setCarbs(updatedProduct.getCarbs());
        existingProduct.setAlcohol(updatedProduct.getAlcohol());
        Product savedProduct = productRepository.save(existingProduct);
        LOGGER.info("Update product with id: {} and description: {}", existingProduct.getId(), existingProduct.getDescription());
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
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
    public ResponseEntity<Product> findByDescription(@PathVariable String description) {
        Optional<Product> product = productRepository.findByDescription(description);
        if (product.isPresent()) {
            LOGGER.info("Found product with description: {} ", description);
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            throw new ProductNotFoundException(description);
        }
    }

//    @RequestMapping(value = "/findByDescriptionContaining/{description}", method = RequestMethod.GET)
//    public ResponseEntity<List<Product>> findByDescriptionContaining(@PathVariable String description) {
//        List<Product> products = productRepository.findByDescriptionContaining(description);
//        if (!products.isEmpty()) {
//            LOGGER.info("Found {} products with description: {} ", products.size(), description);
//            return new ResponseEntity<>(products, HttpStatus.OK);
//        } else {
////            throw new ProductNotFoundException(description);
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        }
//    }

    @RequestMapping(value = "/findByDescriptionContaining/{description}", method = RequestMethod.GET)
    public ResponseEntity<List<FrontEndProduct>> findByDescriptionContaining(@PathVariable String description) {
        List<Product> products = productRepository.findByDescriptionContaining(description);
        List<FrontEndProduct> frontEndProducts = new ArrayList<>();
        for(Product product : products){
            frontEndProducts.add(new FrontEndProduct(product, 1));
        }
        if (!products.isEmpty()) {
            LOGGER.info("Found {} products with description: {} ", products.size(), description);
            return new ResponseEntity<>(frontEndProducts, HttpStatus.OK);
        } else {
            //            throw new ProductNotFoundException(description);
            return new ResponseEntity<>(HttpStatus.OK);

        }
    }

    private void findIfProductExist(long productId) {
        productRepository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private Product findIfProductExist(String description) {
        return productRepository.findByDescription(description)
            .orElseThrow(() -> new ProductNotFoundException(description));
    }


}
