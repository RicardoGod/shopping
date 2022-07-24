package com.backend.shopping.model;

import lombok.Data;

public class Buyer extends User{

  public Buyer() {
    super();
    this.role = Role.BUYER;
  }

  public Buyer(Long id, String username, String password, Long deposit) {
    super(id, username, password, deposit, Role.BUYER);
  }
}
