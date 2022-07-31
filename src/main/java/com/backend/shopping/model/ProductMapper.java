package com.backend.shopping.model;

import com.backend.shopping.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  void updateProductFromDto(ProductDTO dto, @MappingTarget Product entity);
}
