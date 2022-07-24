package com.backend.shopping.model;

public class Seller extends User{

  public Seller() {
    super();
    this.role = Role.SELLER;
  }

  public Seller(Long id, String username, String password, Long deposit) {
    super(id, username, password, deposit, Role.SELLER);
  }
}
