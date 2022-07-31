package com.backend.shopping.model;

import com.backend.shopping.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  Long amountAvailable;
  Long cost;
  String productName;
  @ManyToOne(cascade = CascadeType.ALL)
  User seller;

  public Product(ProductDTO productDTO) {
    this.productName=productDTO.getProductName();
    this.amountAvailable = productDTO.getAmountAvailable();
    this.cost = productDTO.getCost();
  }
}
