package com.vno.user.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vno.user.dto.UserRequest;
import com.vno.user.dto.UserResponse;
import com.vno.user.exception.ResourceNotFoundException;
import com.vno.user.mapper.UserMapper;
import com.vno.user.model.User;
import com.vno.user.model.enums.UserStatus;
import com.vno.user.repository.UserRepository;
import com.vno.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserResponse createUser(UserRequest userRequest) {
    log.info("Creating new user with username: {}", userRequest.getUsername());

    // Check if username or email already exists
    if (userRepository.existsByUsername(userRequest.getUsername())) {
      throw new IllegalArgumentException("Username is already taken");
    }

    if (userRepository.existsByEmail(userRequest.getEmail())) {
      throw new IllegalArgumentException("Email is already in use");
    }
    // Map and save user
    User user = userMapper.toEntity(userRequest);
    userRequest.setStatus(UserStatus.PENDING_VERIFICATION);
    // Encode password if provided
    if (userRequest.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }

    user = userRepository.save(user);
    log.info("Created user with id: {}", user.getId());

    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getUserById(UUID id) {
    log.debug("Fetching user with id: {}", id);
    User user = getEntityById(id);
    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserResponse> getAllUsers(Pageable pageable) {
    log.debug("Fetching all users");
    return userRepository.findAll(pageable).map(userMapper::toResponse);
  }

  @Override
  @Transactional
  public UserResponse updateUser(UUID id, UserRequest userRequest) {
    log.info("Updating user with id: {}", id);

    User existingUser = getEntityById(id);

    // Check if new username is taken by another user
    if (!existingUser.getUsername().equals(userRequest.getUsername())
        && userRepository.existsByUsername(userRequest.getUsername())) {
      throw new IllegalArgumentException("Username is already taken");
    }

    // Check if new email is taken by another user
    if (!existingUser.getEmail().equals(userRequest.getEmail())
        && userRepository.existsByEmail(userRequest.getEmail())) {
      throw new IllegalArgumentException("Email is already in use");
    }

    // Update user fields
    userMapper.updateEntity(userRequest, existingUser);

    // Encode password if provided
    if (userRequest.getPassword() != null) {
      existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }

    User updatedUser = userRepository.save(existingUser);
    log.info("Updated user with id: {}", id);

    return userMapper.toResponse(updatedUser);
  }

  @Override
  @Transactional
  public void deleteUser(UUID id) {
    log.info("Deleting user with id: {}", id);
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User", "id", id);
    }
    userRepository.deleteById(id);
    log.info("Deleted user with id: {}", id);
  }

  @Override
  @Transactional(readOnly = true)
  public User getEntityById(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  }

  @Override
  public UserResponse toResponse(User user) {
    return userMapper.toResponse(user);
  }
}
