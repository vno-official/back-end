package com.vno.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  @Pattern(
      regexp = "^[a-zA-Z0-9_-]+$",
      message = "Username can only contain letters, numbers, underscores and hyphens")
  private String username;

  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
      message =
          "Password must contain at least one uppercase letter, one lowercase letter, and one number")
  private String password;

  @Size(max = 100, message = "First name must not exceed 100 characters")
  private String firstName;

  @Size(max = 100, message = "Last name must not exceed 100 characters")
  private String lastName;

  @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Invalid phone number format")
  private String phoneNumber;
}
