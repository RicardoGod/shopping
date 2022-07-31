package com.backend.shopping.model;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Deposit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(mappedBy = "deposit")
  List<Coin> coins = new ArrayList<>();

  @OneToOne(mappedBy = "deposit")
  User user;

  @Override
  public String toString() {
    return "Deposit{" +
        "id=" + id +
        ", coins=" + coins +
        '}';
  }
}
