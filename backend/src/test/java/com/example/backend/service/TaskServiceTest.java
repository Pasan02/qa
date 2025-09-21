package com.example.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit tests for TaskService.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    void createTaskRejectsBlankTitle() {
        TaskRequest request = new TaskRequest("   ", "some description");

        assertThatThrownBy(() -> taskService.createTask(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task title must not be blank");
    }

    @Test
    void createTaskReturnsTaskWithCorrectFields() {
        TaskRequest request = new TaskRequest("Test Task", "A sample description");
        Task task = taskService.createTask(request);

        assertThat(task.getTitle()).isEqualTo("Test Task");
        assertThat(task.getDescription()).isEqualTo("A sample description");
        assertThat(task.getId()).isNotNull(); // Should have an ID from database
    }
}
