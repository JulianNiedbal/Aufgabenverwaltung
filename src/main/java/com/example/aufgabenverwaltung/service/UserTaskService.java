package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;
import com.example.aufgabenverwaltung.model.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service
public class UserTaskService {
    UserService userService;
    TaskService taskService;

    public UserTaskService(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    // Todo testen

    public Optional<User> updateUser(String username, UserInsertionDto userInsertionDto) {
        taskService.changeOwnerUsername(username, userInsertionDto.getUsername());
        return userService.updateUser(username, userInsertionDto);
    }

    @Transactional
    public boolean deleteUser(String username) {
        taskService.deleteAllTasksByOwnerUsername(username);
        return userService.deleteUserByUsername(username);
    }

    public Optional<Task> createTask(@RequestHeader("X-User") String username, TaskInsertionDto taskInsertionDto) {
        if (userService.getUserByUsername(username).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(taskService.createTask(username, taskInsertionDto));
    }

}
