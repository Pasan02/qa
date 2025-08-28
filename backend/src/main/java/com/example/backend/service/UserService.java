package com.example.backend.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Service for managing users.
 */
@Service
@Validated
public class UserService {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public User signup(@Valid SignupRequest request) {
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String message = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(message);
        }
        // In real app, save to DB, hash password, etc.
        return new User(request.email(), request.password());
    }
}
