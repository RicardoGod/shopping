package com.backend.shopping.model;

import com.backend.shopping.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


  @Mapping(target = "role", ignore = true)
  void updateCustomerFromDto(UserDTO dto, @MappingTarget User entity);
}
