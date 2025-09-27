package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.repository.TaskRepository;

// ...existing imports...

/**
 * Service for managing tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Creates a new Task from the given request.
     * @param request TaskRequest DTO
     * @return Task
     * @throws IllegalArgumentException if title is blank
     */
    public Task createTask(TaskRequest request) {
        // Additional validation if needed
        if (request.title() == null || request.title().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title must not be blank");
        }
        
        // Create and save task to database
        Task task = new Task(request.title(), request.description());
        return taskRepository.save(task);
    }

    public Task createTask(TaskRequest request, Long ownerId) {
        Task task = new Task(request.title(), request.description());
        if (ownerId != null) {
            User owner = new User();
            owner.setId(ownerId);
            task.setOwner(owner);
        }
        return taskRepository.save(task);
    }
}
