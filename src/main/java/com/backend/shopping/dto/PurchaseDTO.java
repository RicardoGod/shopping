package com.backend.shopping.dto;

import com.backend.shopping.model.Product;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PurchaseDTO {
  Long totalSpent;
  Product product;
  Map<CoinDTO, Integer> change = new HashMap<>();

  public PurchaseDTO(Long totalSpent, Product product) {
    this.totalSpent = totalSpent;
    this.product = product;
    change.put(CoinDTO.FIVE_CENTS,0);
    change.put(CoinDTO.TEN_CENTS,0);
    change.put(CoinDTO.TWENTY_CENTS,0);
    change.put(CoinDTO.FIFTY_CENTS, 0);
    change.put(CoinDTO.ONE_HUNDRED_CENTS,0);
  }
}
