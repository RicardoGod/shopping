package com.backend.shopping.model;

import com.backend.shopping.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  void updateProductFromDto(ProductDTO dto, @MappingTarget Product entity);
}
