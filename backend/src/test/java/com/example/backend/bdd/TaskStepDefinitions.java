package com.example.backend.bdd;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.service.Task;
import com.example.backend.service.TaskRequest;
import com.example.backend.service.TaskService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TaskStepDefinitions {

    private TaskService taskService;
    private List<Task> taskList;

    @Given("I am a registered user")
    public void i_am_a_registered_user() {
        taskService = new TaskService();
        taskList = new ArrayList<>();
    }

    @When("I add a task with title {string} and description {string}")
    public void i_add_a_task(String title, String description) {
        Task task = taskService.createTask(new TaskRequest(title, description));
        taskList.add(task);
    }

    @Then("the task list should contain {string}")
    public void the_task_list_should_contain(String expectedTitle) {
        assertThat(taskList.stream().anyMatch(t -> t.title().equals(expectedTitle)))
                .isTrue();
    }
}
