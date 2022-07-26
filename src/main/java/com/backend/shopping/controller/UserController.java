package com.backend.shopping.controller;

import com.backend.shopping.dto.CartDTO;
import com.backend.shopping.dto.DepositDTO;
import com.backend.shopping.dto.PurchaseDTO;
import com.backend.shopping.model.User;
import com.backend.shopping.service.CartService;
import com.backend.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  CartService cartService;

  @PostMapping(path = "/")
  @ResponseStatus(HttpStatus.CREATED)
  public User register(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @PostMapping(path = "/deposit")
  @ResponseStatus(HttpStatus.OK)
  public User deposit(@RequestBody DepositDTO deposit, @RequestParam Long userId) {
    return userService.addDeposit(deposit, userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping(path = "/buy")
  @ResponseStatus(HttpStatus.OK)
  public PurchaseDTO buy(@RequestBody CartDTO cart, @RequestParam Long userId) {
    return cartService.purchaseCart(cart, userId);
  }

  @PostMapping(path = "/reset")
  @ResponseStatus(HttpStatus.OK)
  public User reset(@RequestParam Long userId) {
    return userService.resetDeposit(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
