package com.example.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for TaskService.
 */
public class TaskServiceTest {

    private final TaskService taskService = new TaskService();

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

        assertThat(task.title()).isEqualTo("Test Task");
        assertThat(task.description()).isEqualTo("A sample description");
    }
}
