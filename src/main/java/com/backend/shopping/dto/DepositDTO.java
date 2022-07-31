package com.backend.shopping.dto;

import com.backend.shopping.model.Deposit;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepositDTO {

  List<CoinDTO> coins = new ArrayList<>();

  public DepositDTO(Deposit deposit){
    deposit.getCoins().stream()
        .map(coin -> new CoinDTO(coin.getCoinValue()))
        .forEach(coin -> this.coins.add(coin));
  }

}
