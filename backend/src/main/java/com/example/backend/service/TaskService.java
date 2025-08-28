package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * Service for managing tasks.
 */
@Service
@Validated
public class TaskService {

    /**
     * Creates a new Task from the given request.
     * @param request TaskRequest DTO
     * @return Task
     * @throws IllegalArgumentException if title is blank
     */
    public Task createTask(@Valid TaskRequest request) {
        // Additional validation if needed
        if (request.title() == null || request.title().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title must not be blank");
        }
        // Here you’d normally save to DB, for now return dummy Task
        return new Task(request.title(), request.description());
    }
}
