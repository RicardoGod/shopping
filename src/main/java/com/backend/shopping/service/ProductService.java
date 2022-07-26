package com.backend.shopping.service;

import com.backend.shopping.model.Product;
import com.backend.shopping.repository.ProductRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  public Product addProduct(Product product) {
    return productRepository.save(product);
  }

  public Optional<Product> find(Long productId) {
    return productRepository.findById(productId);
  }

  public Optional<Product> update(Long productId, Product product) {
    productRepository.findById(productId).ifPresent(ignored -> productRepository.save(product));
    return productRepository.findById(productId);
  }

  public Optional<Product> remove(Long productId) {
    Optional<Product> product = productRepository.findById(productId);
    product.ifPresent(p -> productRepository.delete(p));
    return product;
  }
}
