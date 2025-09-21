package com.example.backend.bdd;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.service.Task;
import com.example.backend.service.TaskRequest;
import com.example.backend.service.TaskService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TaskStepDefinitions {

    @Autowired
    private TaskService taskService;
    
    private List<Task> taskList;

    @Given("I am a registered user")
    public void i_am_a_registered_user() {
        taskList = new ArrayList<>();
    }

    @When("I add a task with title {string}")
    public void i_add_a_task(String title) {
        Task task = taskService.createTask(new TaskRequest(title, "Default description"));
        taskList.add(task);
    }

    @Then("the task list should contain {string}")
    public void the_task_list_should_contain(String expectedTitle) {
        assertThat(taskList.stream().anyMatch(t -> t.getTitle().equals(expectedTitle)))
                .isTrue();
    }
}
