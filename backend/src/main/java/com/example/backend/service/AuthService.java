package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.repository.UserRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // token -> userId
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();

    public String login(String email, String password) {
        var opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) return null;
        User u = opt.get();
        if (!u.getPassword().equals(password)) return null;
        String token = UUID.randomUUID().toString();
        tokens.put(token, u.getId());
        return token;
    }

    public Long userIdFromToken(String token) {
        return tokens.get(token);
    }
}
