package com.backend.shopping.model;

import java.util.List;
import javax.persistence.Entity;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String username;
  String password;
  Long deposit;
  Role role;
  @OneToMany
  List<Product> product;
}
