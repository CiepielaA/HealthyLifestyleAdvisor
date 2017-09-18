package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.dao.UserDAO;
import com.ciepiela.adrian.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user){
        userDAO.save(user);
        LOGGER.info("Create user with login: {} and e-mail: {}", user.getLogin(), user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public ResponseEntity deleteUser(@PathVariable long userId){
        userDAO.delete(userId);
        LOGGER.info("Delete user with id {}", userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User userUpdate){
        User user = userDAO.getOne(userUpdate.getUserId());
        user.setEmail(userUpdate.getEmail());
        user.setLogin(userUpdate.getLogin());
        user.setPassword(userUpdate.getPassword());
        userDAO.save(user);
        LOGGER.info("Update user with id {}", user.getUserId());
        return new ResponseEntity(HttpStatus.OK);
    }


}
