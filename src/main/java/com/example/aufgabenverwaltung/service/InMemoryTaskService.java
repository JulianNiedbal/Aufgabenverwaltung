package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.TaskInsertionDto;
import com.example.aufgabenverwaltung.model.entities.Task;
import com.example.aufgabenverwaltung.model.mapper.TaskMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("inMemory")
public class InMemoryTaskService implements TaskService {

    private final Map<String, List<Task>> taskStorage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public List<Task> getTasks(String username) {
        return taskStorage.getOrDefault(username, new ArrayList<Task>());
    }

    @Override
    public Optional<Task> getTaskById(String username, Long id) {
        return taskStorage.getOrDefault(username, new ArrayList<Task>())
                .stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    @Override
    public Task createTask(String username, TaskInsertionDto taskDto) {
        Task task = TaskMapper.toEntity(username, taskDto);
        task.setId(idGenerator.incrementAndGet());
        taskStorage.computeIfAbsent(username, k -> new ArrayList<>()).add(task);
        return task;
    }

    @Override
    public Optional<Task> updateTask(String username, Long id, TaskInsertionDto updatedTask) {
        List<Task> oldTasksOfUser = taskStorage.get(username);
        if (oldTasksOfUser != null) {
            for (int i = 0; i < oldTasksOfUser.size(); i++) {
                if (oldTasksOfUser.get(i).getId().equals(id)) {
                    Task newTask = TaskMapper.toEntity(username, updatedTask);
                    newTask.setId(oldTasksOfUser.get(i).getId());
                    oldTasksOfUser.set(i, newTask);
                    return Optional.of(oldTasksOfUser.get(i));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteTask(String username, Long id) {
        return getTasks(username).removeIf(task -> task.getId().equals(id));
    }

    @Override
    public void changeOwnerUsername(String oldName, String newName) {
        if (taskStorage.containsKey(oldName)) {
            List<Task> oldTasksOfUser = taskStorage.remove(oldName);
            taskStorage.put(newName, oldTasksOfUser.stream().peek(task -> task.setOwnerUsername(newName)).toList());
        }
    }

    @Override
    public void deleteAllTasksByOwnerUsername(String username) {
        //Todo testen
        taskStorage.remove(username);
    }


}
