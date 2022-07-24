package com.backend.shopping.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  String username;
  String password;
  Long deposit;
  Role role;
}
