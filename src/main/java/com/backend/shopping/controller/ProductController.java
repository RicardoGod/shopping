package com.backend.shopping.controller;

import com.backend.shopping.dto.ProductDTO;
import com.backend.shopping.exceptions.InvalidAccessException;
import com.backend.shopping.model.Product;
import com.backend.shopping.service.ProductService;
import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDTO addProduct(@RequestBody ProductDTO productDto, Principal principal) {
    log.info("Received command: AddProduct");
    Product product = productService.addProduct(productDto, principal);
    log.info("Finished command: AddProduct");
    return new ProductDTO(product);
  }

  @PutMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO modifyProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO, Principal principal) {
    try {
      log.info("Received command: ModifyProduct");
      Product product = productService.update(productId, productDTO, principal);
      return new ProductDTO(product);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    } finally {
      log.info("Finished command: ModifyProduct");
    }
  }

  @GetMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO getProductDetails(@PathVariable Integer productId) {
    try {
      log.info("Received command: GetProductDetails");
      Product product = productService.find(productId.longValue());
      return new ProductDTO(product);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    } finally {
      log.info("Finished command: GetProductDetails");
    }
  }

  @DeleteMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO removeProduct(@PathVariable Long productId, Principal principal) {
    try {
      log.info("Received command: GetProductDetails");
      Product product = productService.remove(productId, principal);
      return new ProductDTO(product);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
    } finally {
      log.info("Finished command: RemoveProduct");
    }
  }

}
