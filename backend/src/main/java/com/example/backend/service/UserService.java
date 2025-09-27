package com.example.backend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.backend.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

/**
 * Service for managing users.
 */
@Service
@Validated
public class UserService {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Autowired
    private UserRepository userRepository;

    public User signup(SignupRequest request) {
        // perform validation and throw IllegalArgumentException on first violation
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String message = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(message);
        }
        
        // Check if user already exists
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("User already exists with this email");
        }
        
        // Create and save user to database
        User user = new User(request.email(), request.password());
        return userRepository.save(user);
    }
}
