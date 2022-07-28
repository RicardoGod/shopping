package com.backend.shopping.model;

import com.backend.shopping.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String username;
  String password;
  Long deposit;
  Role role;
  @OneToMany(mappedBy="id")
  List<Product> products = new ArrayList<>();

  public User(UserDTO userDTO) {
    this.username = userDTO.getUsername();
    this.password = userDTO.getPassword();
    this.deposit = userDTO.getDeposit();
    this.role = userDTO.getRole();
  }
}
