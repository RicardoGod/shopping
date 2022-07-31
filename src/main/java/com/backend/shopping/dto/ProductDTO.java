package com.backend.shopping.dto;

import com.backend.shopping.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

  Long id;
  Long amountAvailable;
  Long cost;
  String productName;
  Long userId;

  public ProductDTO(Product product){
    this.id = product.getId();
    this.amountAvailable = product.getAmountAvailable();
    this.cost = product.getCost();
    this.productName = product.getProductName();
    this.userId = product.getSeller() != null ? product.getSeller().getId() : null;
  }

}
