package com.backend.shopping.controller;

import com.backend.shopping.dto.ProductDTO;
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
  public ProductDTO addProduct(@RequestBody ProductDTO productDto) {
    Product product = productService.addProduct(productDto);
    return new ProductDTO(product);
  }

  @PutMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO modifyProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
    Product product = productService.update(productId, productDTO)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return new ProductDTO(product);
  }

  @GetMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO getProductDetails(@PathVariable Integer productId) {
    Product product = productService.find(productId.longValue())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return new ProductDTO(product);
  }

  @DeleteMapping(path = "/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO removeProduct(@PathVariable Long productId) {
    Product product = productService.remove(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return new ProductDTO(product);
  }

}
