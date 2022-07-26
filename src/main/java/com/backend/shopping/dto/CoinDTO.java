package com.backend.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoinDTO {
  FIVE_CENTS(5),
  TEN_CENTS(10),
  TWENTY_CENTS(20),
  FIFTY_CENTS(50),
  ONE_HUNDRED_CENTS(100);

  private int value;

}
