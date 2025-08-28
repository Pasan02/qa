Feature: Task management

  Scenario: Add a new task
    Given I am a registered user
    When I add a task with title "Buy milk"
    Then the task list should contain "Buy milk"
