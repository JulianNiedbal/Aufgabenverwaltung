package com.example.aufgabenverwaltung.model.mapper;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;

public class TaskMapper {
    public static Task toEntity(String username, TaskInsertionDto dto) {
        Task task = new Task();
        task.setOwnerUsername(username);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        return task;
    }
}
