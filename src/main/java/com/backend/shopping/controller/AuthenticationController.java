package com.backend.shopping.controller;

import com.backend.shopping.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class AuthenticationController {

  @PostMapping(path = "/login")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Object> login() {
    log.info("Received command: Login");
    log.info("Finished command: Login");
    return ResponseEntity.accepted().build();
  }

  @PostMapping(path = "/logout")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Object> logout(@RequestBody UserDTO userDTO) {
    log.info("Received command: Logout");
    log.info("Finished command: Logout");
    return ResponseEntity.accepted().build();
  }

}
