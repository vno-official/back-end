package com.vno.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MagicLinkRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    public String email;
}
