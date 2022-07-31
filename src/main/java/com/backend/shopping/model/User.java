package com.backend.shopping.model;

import com.backend.shopping.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String username;
  String password;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "deposit_id", referencedColumnName = "id")
  Deposit deposit;

  @ManyToOne
  Role role;

  @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
  List<Product> products = new ArrayList<>();

  public User(UserDTO userDTO) {
    this.username = userDTO.getUsername();
    this.password = userDTO.getPassword();
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", deposit=" + deposit +
        ", role=" + role +
        ", products=" + products +
        '}';
  }
}
