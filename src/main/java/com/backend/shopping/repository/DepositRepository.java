package com.backend.shopping.repository;

import com.backend.shopping.model.Deposit;
import com.backend.shopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

}
