package com.backend.shopping.service;

import com.backend.shopping.model.User;
import com.backend.shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User registerUser(User user){
    return userRepository.save(user);
  }

}
