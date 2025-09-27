package com.example.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.backend.repository.TaskRepository;
import com.example.backend.service.Task;

import jakarta.annotation.PostConstruct;

@Component
@Profile("!test")
public class StartupDataInitializer {

    private static final Logger log = LoggerFactory.getLogger(StartupDataInitializer.class);

    private final TaskRepository taskRepository;

    public StartupDataInitializer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void init() {
        try {
            if (taskRepository.count() == 0) {
                taskRepository.save(new Task("Init Task", "Created on startup"));
                log.info("Default task created by StartupDataInitializer");
            }
        } catch (Exception ex) {
            log.warn("Failed to initialize default tasks: {}", ex.getMessage());
        }
    }
}
