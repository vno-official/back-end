package com.vno.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${USER_SERVICE_URL:http://localhost:8082}")
public interface UserServiceClient {

  @PostMapping("/internal/auth/verify")
  VerifyResponse verify(@RequestBody VerifyRequest request);

  @PostMapping("/api/users")
  ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest request);

  @lombok.Data
  class VerifyRequest {
    private String usernameOrEmail;
    private String password;
  }

  @lombok.Data
  class VerifyResponse {
    private java.util.UUID id;
    private String username;
    private String email;
  }

  @lombok.Data
  class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
  }

  @lombok.Data
  class UserResponse {
    private java.util.UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
  }

  @lombok.Data
  class ApiResponse<T> {
    private boolean success;
    private String message;
    private String code;
    private T data;
    private long timestamp;
  }
}
