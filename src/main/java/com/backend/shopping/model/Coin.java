package com.backend.shopping.model;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Coin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Enumerated(EnumType.STRING)
  CoinValue coinValue;

  @ManyToOne
  Deposit deposit;

  public Coin(CoinValue coinValue) {
    this.coinValue = coinValue;
  }

  @Override
  public String toString() {
    return "Coin{" +
        "id=" + id +
        ", coinValue=" + coinValue +
        '}';
  }
}
