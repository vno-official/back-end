package com.vno.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vno.user.dto.UserRequest;
import com.vno.user.dto.UserResponse;
import com.vno.user.model.User;

public interface UserService {
  UserResponse createUser(UserRequest userRequest);

  UserResponse getUserById(UUID id);

  Page<UserResponse> getAllUsers(Pageable pageable);

  UserResponse updateUser(UUID id, UserRequest userRequest);

  void deleteUser(UUID id);

  User getEntityById(UUID id);

  UserResponse toResponse(User user);
}
