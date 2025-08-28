package com.example.backend.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    void signup_rejectsShortPassword() {
        SignupRequest request = new SignupRequest("user@example.com", "12345");

        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must be at least 8 characters long");
    }

    @Test
    void signup_acceptsValidPassword() {
        SignupRequest request = new SignupRequest("user@example.com", "strongpass");

        User user = userService.signup(request);

        assertThat(user.getEmail()).isEqualTo("user@example.com");
    }
}
