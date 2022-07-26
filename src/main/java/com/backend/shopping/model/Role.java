package com.backend.shopping.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Role implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Enumerated(value= EnumType.STRING)
  RoleCategory name;

  @OneToMany(mappedBy="role")
  List<User> users = new ArrayList<>();

  @Override
  public String toString() {
    return "Role{" +
        "name=" + name +
        '}';
  }

  @Override
  public String getAuthority() {
    return name.toString();
  }
}

