package com.ciepiela.adrian.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserNotFoundException(long userId) {
        LOGGER.warn("Could not find user with id: {}", userId);
    }
    public UserNotFoundException(String userEmail) {
        LOGGER.warn("Could not find user with email: {}", userEmail);
    }

}
