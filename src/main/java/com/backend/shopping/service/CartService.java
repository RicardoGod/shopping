package com.backend.shopping.service;

import com.backend.shopping.dto.CartDTO;
import com.backend.shopping.dto.PurchaseDTO;
import com.backend.shopping.model.Product;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.ProductRepository;
import com.backend.shopping.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  public PurchaseDTO purchaseCart(CartDTO cart, Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Product> optionalProduct = productRepository.findById(cart.getProductId());

    if(optionalUser.isPresent() && optionalProduct.isPresent()){
      Product product = optionalProduct.get();
      Long quantityRemaining = product.getAmountAvailable() - cart.getQuantity();
      product.setAmountAvailable(quantityRemaining);
      Long totalCost = product.getCost() * cart.getQuantity();

      User user = optionalUser.get();
      Long currentBalance = user.getDeposit();
      user.setDeposit(currentBalance - totalCost);

      userRepository.save(user);
      productRepository.save(product);

      return new PurchaseDTO(totalCost, product);
    }

    throw new IllegalArgumentException();
  }
}
