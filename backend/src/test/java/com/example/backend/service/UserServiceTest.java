package com.example.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
        assertThat(user.getId()).isNotNull(); // Should have an ID from database
    }

    @Test
    void signup_rejectsDuplicateEmail() {
        SignupRequest request1 = new SignupRequest("user@example.com", "strongpass");
        SignupRequest request2 = new SignupRequest("user@example.com", "anotherpass");

        userService.signup(request1);

        assertThatThrownBy(() -> userService.signup(request2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User already exists with this email");
    }
}
