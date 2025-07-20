package com.example.aufgabenverwaltung.controller;

import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.User;
import com.example.aufgabenverwaltung.service.UserService;
import com.example.aufgabenverwaltung.service.UserTaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    UserService userService;
    UserTaskService userTaskService;

    public UserController(UserService userService, UserTaskService userTaskService) {
        this.userService = userService;
        this.userTaskService = userTaskService;
    }

    @GetMapping
    public Optional<User> getUserByUsername(@RequestHeader("X-User") String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("user")
    public User addUser(@RequestBody UserInsertionDto userInsertionDto) {
        return userService.createUser(userInsertionDto);
    }

    @PutMapping("user")
    public Optional<User> updateUser(@RequestHeader("X-User") String username, @RequestBody UserInsertionDto userInsertionDto) {
        return userTaskService.updateUser(username, userInsertionDto);
    }

    @DeleteMapping("user")
    public void deleteUser(@RequestHeader("X-User") String username) {
        userTaskService.deleteUser(username);
    }


}
