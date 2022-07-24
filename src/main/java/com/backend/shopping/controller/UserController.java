package com.backend.shopping.controller;

import com.backend.shopping.model.User;
import com.backend.shopping.repository.UserRepository;
import com.backend.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping(path = "/")
  @ResponseStatus(HttpStatus.CREATED)
  public User register(@RequestBody User user) {
    return userService.registerUser(user);
  }

}
