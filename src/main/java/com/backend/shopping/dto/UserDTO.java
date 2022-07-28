package com.backend.shopping.dto;

import com.backend.shopping.model.Product;
import com.backend.shopping.model.Role;
import com.backend.shopping.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

  Long id;
  String username;
  String password;
  Long deposit;
  Role role;
  List<Long> productIds;

  public UserDTO(User user){
    this.id = user.getId();
    this.username = user.getUsername();
    this. password = user.getPassword();
    this.deposit = user.getDeposit();
    this.role = user.getRole();
    this.productIds = user.getProducts().stream()
        .map(Product::getId)
        .collect(Collectors.toList());
  }

}
