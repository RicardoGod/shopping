package com.backend.shopping.controller;

import com.backend.shopping.model.Product;
import com.backend.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  ProductService productService;

  @PostMapping(path = "/")
  @ResponseStatus(HttpStatus.CREATED)
  public Product addProduct(@RequestBody Product product) {
    return productService.addProduct(product);
  }

  @PutMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Product modifyProduct(@PathVariable String productId, @RequestBody Product product) {
    return productService.update(Long.getLong(productId), product)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Product getProductDetails(@PathVariable Integer productId) {
    return productService.find(productId.longValue())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Product removeProduct(@PathVariable Long productId) {
    return productService.remove(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

}
