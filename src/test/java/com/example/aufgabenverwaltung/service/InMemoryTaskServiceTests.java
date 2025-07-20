package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InMemoryTaskServiceTests {


    private InMemoryTaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new InMemoryTaskService();
    }

    @Test
    void createTask_ShouldAssignIdAndStoreTask() {
        String userName = "JulianNiedbal";
        TaskInsertionDto apiTask = new TaskInsertionDto("Tests schreiben", "Schreibe Unit Tests für den inMemoryTaskService", false);
        Task createdTask = taskService.createTask(userName, apiTask);

        assertNotNull(createdTask.getId(), "Task sollte erstellt und Id gesetzt sein");
    }

    @Test
    void getTasks_ShouldReturnAllTasksByUser() {
        String userA = "User A";
        String userB = "User B";

        taskService.createTask(userA, new TaskInsertionDto("Task A 1", "Test Task", false));
        taskService.createTask(userA, new TaskInsertionDto("Task A 2", "Test Task", false));

        taskService.createTask(userB, new TaskInsertionDto("Task B 1", "Test Task", false));

        List<Task> tasks = taskService.getTasks(userA);

        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().allMatch(task -> task.getOwnerUsername().equals(userA)), "Alle Tasks sollten zum User " + userA + " gehören");
        assertFalse(tasks.stream().allMatch(task -> task.getOwnerUsername().equals(userB)), "Keine Tasks sollten zum User " + userB + " gehören");
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        String userC = "User C";
        Task createdTask1 = taskService.createTask(userC, new TaskInsertionDto("Task C 1", "Test Task", false));
        Task createdTask2 = taskService.createTask(userC, new TaskInsertionDto("Task C 2", "Test Task", false));

        Optional<Task> testTask = taskService.getTaskById(userC, createdTask1.getId());

        assertTrue(testTask.isPresent(), "Task wurde nicht gefunden");
        assertEquals(createdTask1.getId(), testTask.get().getId(), "Task hat nicht ursprünglich generierte Id");
        assertNotEquals(createdTask2.getId(), testTask.get().getId(), "Task hat die selbe Id wie ein anderer Task");
    }

    @Test
    void updateTask_ShouldUpdateTaskIfItExists() {
        String userC = "User C";

        Task originalTask = taskService.createTask(userC, new TaskInsertionDto("Task C 1", "Test Task", false));
        TaskInsertionDto updatedTask = new TaskInsertionDto("Task C 2", "Test Task", false);
        Optional<Task> testTask = taskService.updateTask(userC, originalTask.getId(), updatedTask);

        assertTrue(testTask.isPresent(), "Task wurde nicht gefunden");
        assertEquals(testTask.get().getTitle(), updatedTask.getTitle(), "Task Titel wurde nicht geupdated");
        assertNotEquals(testTask.get().getTitle(), originalTask.getTitle(), "Task Titel wurde nicht geupdated");
    }

    @Test
    void updateTask_ShouldReturnEmptyListIfTaskDoesNotExist() {
        String userC = "User C";

        Task originalTask = taskService.createTask(userC, new TaskInsertionDto("Task C 1", "Test Task", false));
        TaskInsertionDto updatedTask = new TaskInsertionDto("Task C 2", "Test Task", false);
        Optional<Task> testTask = taskService.updateTask(userC, originalTask.getId() + 1, updatedTask);

        assertFalse(testTask.isPresent(), "Task mit übergebener Id sollte nicht existieren");
    }

    @Test
    void deleteTask_ShouldDeleteTaskIfItExists() {
        String userC = "User C";
        Task originalTask = taskService.createTask(userC, new TaskInsertionDto("Task C 1", "Test Task", false));
        boolean taskDeleted = taskService.deleteTask(userC, originalTask.getId());
        Optional<Task> testTask = taskService.getTaskById(userC, originalTask.getId());

        assertTrue(taskDeleted, "Es sollte zurückgegeben worden sein, dass der Task gelöscht wurde");
        assertFalse(testTask.isPresent(), "Task wurde nicht gelöscht");
    }

    @Test
    void deleteTask_ShouldDoNothingIfItDoesNotExist() {
        String userC = "User C";

        Task originalTask = taskService.createTask(userC, new TaskInsertionDto("Task C 1", "Test Task", false));
        long falseId = originalTask.getId() + 1;
        boolean taskDeleted = taskService.deleteTask(userC, falseId);
        Optional<Task> testTask = taskService.getTaskById(userC, originalTask.getId());

        assertFalse(taskDeleted, "Es sollte zurückgegeben worden sein, dass der Task nicht gelöscht wurde");
        assertFalse(testTask.isEmpty(), "Task hätte nicht gelöscht werden dürfen");
    }

}
