package com.vno.user.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vno.common.dto.ApiResponse;
import com.vno.user.dto.UserRequest;
import com.vno.user.dto.UserResponse;
import com.vno.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

  private final UserService userService;

  @PostMapping
  @Operation(summary = "Create a new user")
  public ResponseEntity<ApiResponse<UserResponse>> createUser(
      @Valid @RequestBody UserRequest userRequest) {
    UserResponse user = userService.createUser(userRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success("User created successfully", user));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID")
  public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
    UserResponse user = userService.getUserById(id);
    return ResponseEntity.ok(ApiResponse.success(user));
  }

  @GetMapping
  @Operation(summary = "Get all users with pagination")
  public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(Pageable pageable) {
    Page<UserResponse> users = userService.getAllUsers(pageable);
    return ResponseEntity.ok(ApiResponse.success(users));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing user")
  public ResponseEntity<ApiResponse<UserResponse>> updateUser(
      @PathVariable UUID id, @Valid @RequestBody UserRequest userRequest) {
    UserResponse updatedUser = userService.updateUser(id, userRequest);
    return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a user")
  public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
  }

  @GetMapping("/{userId}/exists")
  @Operation(summary = "Check if user exists")
  public ResponseEntity<Boolean> userExists(@PathVariable UUID userId) {
    boolean exists = userService.getUserById(userId) != null;
    return ResponseEntity.ok(exists);
  }
}
