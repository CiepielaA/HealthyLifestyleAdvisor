package com.ciepiela.adrian.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExists extends RuntimeException {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserAlreadyExists(String email) {
        LOGGER.warn("User with email: {} already exists", email);
    }

}
