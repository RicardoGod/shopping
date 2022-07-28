package com.backend.shopping.service;

import com.backend.shopping.dto.DepositDTO;
import com.backend.shopping.dto.UserDTO;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User registerUser(UserDTO userDTO){
    User user = new User(userDTO);
    return userRepository.save(user);
  }

  public Optional<User> addDeposit(DepositDTO deposit, Long userId) {
    Optional<User> user = userRepository.findById(userId);
    user.ifPresent(u -> u.setDeposit(u.getDeposit() + deposit.getValue().getValue()));
    return user;
  }

  public Optional<User> resetDeposit(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    user.ifPresent(u -> u.setDeposit(0L));
    user.ifPresent(value -> userRepository.save(value));
    return user;
  }

  public Optional<User> find(Long userId) {
    return userRepository.findById(userId);
  }
}
