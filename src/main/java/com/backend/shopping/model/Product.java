package com.backend.shopping.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  Long amountAvailable;
  Long cost;
  String productName;
  @OneToOne
  User seller;
}
