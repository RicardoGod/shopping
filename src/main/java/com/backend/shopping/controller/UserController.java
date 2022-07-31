package com.backend.shopping.controller;

import com.backend.shopping.dto.CartDTO;
import com.backend.shopping.dto.CoinDTO;
import com.backend.shopping.dto.PurchaseDTO;
import com.backend.shopping.dto.UserDTO;
import com.backend.shopping.exceptions.NotEnoughMoneyException;
import com.backend.shopping.model.User;
import com.backend.shopping.service.CartService;
import com.backend.shopping.service.UserService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    log.info("Received command: RegisterUser");
    User user = userService.registerUser(userDTO)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong while creating new User!"));;
    log.info("Finished command: RegisterUser");
    return new UserDTO(user);
  }

  @GetMapping(path = "/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO getUserDetails(@PathVariable Long userId) {
    log.info("Received command: GetUserDetails");
    User user = userService.find(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    log.info("Finished command: GetUserDetails");
    return new UserDTO(user);
  }

  @PostMapping(path = "/deposit")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO deposit(@Valid @RequestBody CoinDTO coinDto, @RequestParam Long userId) {
    log.info("Received command: Deposit");
    User user = userService.addDeposit(coinDto, userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    log.info("Finished command: Deposit");
    return new UserDTO(user);
  }

  @PostMapping(path = "/buy")
  @ResponseStatus(HttpStatus.OK)
  public PurchaseDTO buy(@RequestBody CartDTO cart, @RequestParam Long userId) {
    log.info("Received command: Buy");
    try{
      return cartService.purchaseCart(cart, userId);
    } catch (IllegalArgumentException | NotEnoughMoneyException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    } finally {
      log.info("Finished command: Buy");
    }
  }

  @PostMapping(path = "/reset")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO reset(@RequestParam Long userId) {
    log.info("Received command: Reset");
    User user = userService.resetDeposit(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    log.info("Finished command: Reset");
    return new UserDTO(user);
  }
}
