package com.vno.user.dto;

import com.vno.user.model.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "First name is required")
  private String firstName;

  private String lastName;

  @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\s./0-9]*$", message = "Invalid phone number format")
  private String phoneNumber;

  private String password;
  private UserStatus status;
  private String avatarUrl;
  private String timezone;
  private String locale;
}
