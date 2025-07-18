package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getTasks(String username);

    Optional<Task> getTaskById(String username, Long id);

    Task createTask(String username, TaskInsertionDto taskDto);

    Optional<Task> updateTask(String username, Long id, TaskInsertionDto taskDto);

    boolean deleteTask(String username, Long id);
}
