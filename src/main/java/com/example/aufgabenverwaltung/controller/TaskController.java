package com.example.aufgabenverwaltung.controller;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;
import com.example.aufgabenverwaltung.service.TaskService;
import com.example.aufgabenverwaltung.service.UserTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    TaskService taskService;
    UserTaskService userTaskService;

    public TaskController(TaskService taskService, UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public List<Task> getTasks(@RequestHeader("X-User") String username) {
        return taskService.getTasks(username);
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Task> getTaskById(@RequestHeader("X-User") String username, @PathVariable("id") Long id) {
        return taskService.getTaskById(username, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("tasks")
    public Optional<Task> addTask(@RequestHeader("X-User") String username, @RequestBody TaskInsertionDto taskDto) {
        return userTaskService.createTask(username, taskDto);
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<Task> updateTask(@RequestHeader("X-User") String username, @PathVariable("id") Long id, @RequestBody TaskInsertionDto taskDto) {
        return taskService.updateTask(username, id, taskDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("tasks/{id}")
    public boolean deleteTask(@RequestHeader("X-User") String username, @PathVariable("id") Long id) {
        return taskService.deleteTask(username, id);
    }


}
