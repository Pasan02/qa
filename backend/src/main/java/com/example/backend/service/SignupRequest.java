package com.example.backend.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user signup.
 */
public record SignupRequest(
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    String email,

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password
) {}
