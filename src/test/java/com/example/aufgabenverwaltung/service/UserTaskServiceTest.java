package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserTaskServiceTest {
    
    @Autowired
    UserTaskService userTaskService;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @AfterEach
    public void tearDown() {
        userTaskService.deleteUser("oldUsername");
        userTaskService.deleteUser("newUsername");
        userTaskService.deleteUser("testUsername");
    }

    @Test
    void updateUser_ShouldUpdateTasksIfUserChanges() {
        String oldUsername = "oldUsername";
        String newUsername = "newUsername";
        UserInsertionDto userInsertionDto = new UserInsertionDto(oldUsername);
        TaskInsertionDto taskInsertionDto = new TaskInsertionDto("Test Task", "Task zum Testen", true);

        userService.createUser(userInsertionDto);
        Optional<Task> oldTask = userTaskService.createTask(oldUsername, taskInsertionDto);

        assertTrue(oldTask.isPresent(), "Task wurde nicht gespeichert");

        userTaskService.updateUser(oldUsername, new UserInsertionDto(newUsername));
        Optional<Task> newTask = taskService.getTaskById(newUsername, oldTask.get().getId());

        assertTrue(newTask.isPresent(), "Task kann unter neuem Username nicht gefunden werden");
        assertEquals(newUsername, newTask.get().getOwnerUsername(), "ownerUsername wurde nicht geupdated");
    }

    @Test
    void deleteUser_ShouldDeleteTasksIfUserIsDeleted() {
        String username = "testUsername";
        UserInsertionDto userInsertionDto = new UserInsertionDto(username);
        TaskInsertionDto taskInsertionDto = new TaskInsertionDto("Test Task", "Task zum Testen", true);

        userService.createUser(userInsertionDto);
        Optional<Task> oldTask = userTaskService.createTask(username, taskInsertionDto);

        assertTrue(oldTask.isPresent(), "Task wurde nicht gespeichert");

        userTaskService.deleteUser(username);
        Optional<Task> newTask = taskService.getTaskById(username, oldTask.get().getId());

        assertTrue(newTask.isEmpty(), "Task wurde nicht gelöscht");
    }

}
