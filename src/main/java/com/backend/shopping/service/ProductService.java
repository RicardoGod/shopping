package com.backend.shopping.service;

import com.backend.shopping.dto.ProductDTO;
import com.backend.shopping.exceptions.InvalidAccessException;
import com.backend.shopping.model.Product;
import com.backend.shopping.model.ProductMapper;
import com.backend.shopping.model.RoleCategory;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.ProductRepository;
import com.backend.shopping.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProductMapper productMapper;

  @Transactional(rollbackOn = Exception.class)
  public Product addProduct(ProductDTO productDTO) {
    Optional<User> possibleUser = userRepository.findById(productDTO.getUserId());
    User user = possibleUser.orElseThrow(IllegalArgumentException::new);
    if(user.getRole().getName() != RoleCategory.SELLER){
      throw new InvalidAccessException();
    }
    Product product = new Product(productDTO);
    product.setSeller(user);
    return productRepository.save(product);
  }

  public Optional<Product> find(Long productId) {
    return productRepository.findById(productId);
  }

  @Transactional(rollbackOn = Exception.class)
  public Optional<Product> update(Long productId, ProductDTO productDTO) {
    Optional<Product> product = productRepository.findById(productId);
    product.ifPresent(p -> productMapper.updateProductFromDto(productDTO, p));
    product.ifPresent(p -> productRepository.save(p));
    return productRepository.findById(productId);
  }

  @Transactional(rollbackOn = Exception.class)
  public Optional<Product> remove(Long productId) {
    Optional<Product> product = productRepository.findById(productId);
    product.ifPresent(p -> productRepository.delete(p));
    return product;
  }
}
