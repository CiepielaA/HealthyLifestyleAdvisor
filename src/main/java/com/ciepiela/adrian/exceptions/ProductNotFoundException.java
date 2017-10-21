package com.ciepiela.adrian.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductNotFoundException.class);

    public ProductNotFoundException(long productId) {
        LOGGER.warn("Could not find product with id: {}", productId);
    }

    public ProductNotFoundException(String description) {
        LOGGER.warn("Could not find product with description: {}", description);
    }
}
