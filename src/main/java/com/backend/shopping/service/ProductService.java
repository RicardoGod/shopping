package com.backend.shopping.service;

import com.backend.shopping.dto.ProductDTO;
import com.backend.shopping.exceptions.InvalidAccessException;
import com.backend.shopping.model.Product;
import com.backend.shopping.model.ProductMapper;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.ProductRepository;
import com.backend.shopping.repository.UserRepository;
import java.security.Principal;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
  public Product addProduct(ProductDTO productDTO, Principal principal) {
    User user = userRepository.findByUsername(principal.getName());
    Product product = new Product(productDTO);
    product.setSeller(user);
    return productRepository.save(product);
  }

  public Product find(Long productId) throws NotFoundException {
    return productRepository.findById(productId).orElseThrow(NotFoundException::new);
  }

  @Transactional(rollbackOn = Exception.class)
  public Product update(Long productId, ProductDTO productDTO, Principal principal) throws NotFoundException {
    User user = userRepository.findByUsername(principal.getName());
    Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);

    validateIfUserIsOwnerOfProduct(user, product);

    productMapper.updateProductFromDto(productDTO, product);
    return productRepository.save(product);
  }

  private void validateIfUserIsOwnerOfProduct(User user, Product product) {
    if(!product.getSeller().getId().equals(user.getId())){
      throw new InvalidAccessException();
    }
  }

  @Transactional(rollbackOn = Exception.class)
  public Product remove(Long productId, Principal principal) throws NotFoundException {
    User user = userRepository.findByUsername(principal.getName());
    Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);

    validateIfUserIsOwnerOfProduct(user, product);

    productRepository.delete(product);
    return product;
  }
}
