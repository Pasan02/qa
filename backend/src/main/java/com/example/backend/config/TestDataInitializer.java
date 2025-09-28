package com.example.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.backend.service.SignupRequest;
import com.example.backend.service.UserService;
import jakarta.annotation.PostConstruct;

@Component
@Profile("test")
public class TestDataInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        try {
            userService.signup(new SignupRequest("test@example.com", "password123"));
        } catch (Exception ignored) {
            // Ignore if user already exists
        }
    }
}