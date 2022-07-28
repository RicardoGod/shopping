package com.backend.shopping.controller;

import com.backend.shopping.dto.CartDTO;
import com.backend.shopping.dto.DepositDTO;
import com.backend.shopping.dto.PurchaseDTO;
import com.backend.shopping.dto.UserDTO;
import com.backend.shopping.exceptions.NotEnoughMoneyException;
import com.backend.shopping.model.User;
import com.backend.shopping.service.CartService;
import com.backend.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public UserDTO register(@RequestBody UserDTO userDTO) {
    User user = userService.registerUser(userDTO);
    return new UserDTO(user);
  }

  @GetMapping(path = "/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO getUserDetails(@PathVariable Long userId) {
    User user = userService.find(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return new UserDTO(user);
  }

  @PostMapping(path = "/deposit")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO deposit(@RequestBody DepositDTO deposit, @RequestParam Long userId) {
    User user = userService.addDeposit(deposit, userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return new UserDTO(user);
  }

  @PostMapping(path = "/buy")
  @ResponseStatus(HttpStatus.OK)
  public PurchaseDTO buy(@RequestBody CartDTO cart, @RequestParam Long userId) {
    try{
      return cartService.purchaseCart(cart, userId);
    } catch (IllegalArgumentException | NotEnoughMoneyException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(path = "/reset")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO reset(@RequestParam Long userId) {
    User user = userService.resetDeposit(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return new UserDTO(user);
  }
}
