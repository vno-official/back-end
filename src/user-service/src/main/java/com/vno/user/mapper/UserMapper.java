package com.vno.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.vno.user.dto.UserRequest;
import com.vno.user.dto.UserResponse;
import com.vno.user.model.User;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User toEntity(UserRequest userRequest);

  UserResponse toResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "lastLogin", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  void updateEntity(UserRequest userRequest, @MappingTarget User user);
}
