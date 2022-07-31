package com.backend.shopping.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CoinValue {

  FIVE(5),
  TEN(10),
  TWENTY(20),
  FIFTY(50),
  ONE_HUNDRED(100);

  @JsonValue
  long value;
}
