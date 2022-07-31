package com.backend.shopping.repository;

import com.backend.shopping.model.Role;
import com.backend.shopping.model.RoleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findFirstByName(RoleCategory roleCategory);
}
