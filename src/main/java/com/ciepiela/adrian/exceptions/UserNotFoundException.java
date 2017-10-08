package com.ciepiela.adrian.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotFoundException extends RuntimeException {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserNotFoundException(long userId) {
        LOGGER.warn("Could not find user with id: {}", userId);
    }
    public UserNotFoundException(String userEmail) {
        LOGGER.warn("Could not find user with email: {}", userEmail);
    }

}
