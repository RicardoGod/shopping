package com.backend.shopping.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  Long amountAvailable;
  Long cost;
  String productName;
  @OneToOne
  Seller seller;
}
