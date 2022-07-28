package com.backend.shopping.service;

import com.backend.shopping.dto.ProductDTO;
import com.backend.shopping.model.Product;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.ProductRepository;
import com.backend.shopping.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  public Product addProduct(ProductDTO productDTO) {
    Optional<User> user = userRepository.findById(productDTO.getUserId());
    Product product = new Product(productDTO);
    product.setSeller(user.get());
    return productRepository.save(product);
  }

  public Optional<Product> find(Long productId) {
    return productRepository.findById(productId);
  }

  public Optional<Product> update(Long productId, ProductDTO productDTO) {
    Product product = new Product(productDTO);
    product.setId(productId);
    productRepository.findById(productId).ifPresent(ignored -> productRepository.save(product));
    return productRepository.findById(productId);
  }

  public Optional<Product> remove(Long productId) {
    Optional<Product> product = productRepository.findById(productId);
    product.ifPresent(p -> productRepository.delete(p));
    return product;
  }
}
