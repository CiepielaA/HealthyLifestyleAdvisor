package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.dao.UserRepository;
import com.ciepiela.adrian.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;


@RestController
@RequestMapping(value = "/user")
@EnableWebMvc
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody User user){
        userRepository.save(user);
        LOGGER.info("Create user with login: {} and e-mail: {}", user.getLogin(), user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable long userId){
        userRepository.delete(userId);
        LOGGER.info("Delete user with id {}", userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
    public ResponseEntity update(@RequestBody User updatedUser){
        User user = userRepository.getOne(updatedUser.getUserId());
        user.setEmail(updatedUser.getEmail());
        user.setLogin(updatedUser.getLogin());
        user.setPassword(updatedUser.getPassword());
        userRepository.save(user);
        LOGGER.info("Update user with id {}", user.getUserId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/findById/{userId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long userId){
        Optional<User> user = userRepository.findByUserId(userId);
//        user.ifPresent(u -> LOGGER.info("Found user with id: {} ", userId))
        return new ResponseEntity(HttpStatus.OK);
    }


}
