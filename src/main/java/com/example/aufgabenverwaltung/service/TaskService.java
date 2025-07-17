package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getTasks(String username);
    Optional<Task> getTaskById(String username, Long id);
    Task createTask(String username, Task task);
    Optional<Task> updateTask(String username, Task updatedTask);
    boolean deleteTask(String username, Long id);
}
