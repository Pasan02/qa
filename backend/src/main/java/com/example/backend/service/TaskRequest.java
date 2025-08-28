package com.example.backend.service;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a new Task.
 */
public record TaskRequest(
    @NotBlank(message = "Task title must not be blank")
    String title,
    String description
) {}