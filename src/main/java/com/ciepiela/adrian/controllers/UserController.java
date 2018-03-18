package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.dao.UserRepository;
import com.ciepiela.adrian.exceptions.UserNotFoundException;
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
    public  ResponseEntity create(@RequestBody User user) {
        if (!findIfUserExist(user.getEmail())) {
            User savedUser = userRepository.save(user);
            LOGGER.info("Create user with login: {} and e-mail: {}", user.getLogin(), user.getEmail());
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable long userId) {
        findIfUserExist(userId);
        userRepository.delete(userId);
        LOGGER.info("Delete user with id {}", userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
    public ResponseEntity<User> update(@RequestBody User updatedUser, @PathVariable long userId) {
        findIfUserExist(userId);
        User existingUser = userRepository.getOne(userId);
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setLogin(updatedUser.getLogin());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setDiaryDays(updatedUser.getDiaryDays());
        existingUser.setMeals(updatedUser.getMeals());
        User savedUser = userRepository.save(existingUser);
        LOGGER.info("Update user with id {}", userId);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/findByEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            LOGGER.info("Found user with email: {} ", email);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(email);
        }
    }

    @RequestMapping(value = "/findById/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            LOGGER.info("Found user with id: {} ", userId);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    private void findIfUserExist(long userId) {
        userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private boolean findIfUserExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
