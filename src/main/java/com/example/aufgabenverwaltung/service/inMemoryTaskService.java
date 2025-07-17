package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.Task;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("inMemory")
public class inMemoryTaskService implements TaskService {

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
    public Task createTask(String username, Task task) {
        task.setId(idGenerator.incrementAndGet());
        task.setOwnerUsername(username);
        taskStorage.computeIfAbsent(username, k -> new ArrayList<>()).add(task);
        return task;
    }

    @Override
    public Optional<Task> updateTask(String username, Task updatedTask) {
        List<Task> oldTasksOfUser = taskStorage.get(username);
        if(oldTasksOfUser != null) {
            for(int i = 0; i < oldTasksOfUser.size(); i++) {
                if(oldTasksOfUser.get(i).getId().equals(updatedTask.getId())) {
                    oldTasksOfUser.set(i, updatedTask);
                    return Optional.of(oldTasksOfUser.get(i));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteTask(String username, Long id) {
        getTasks(username).removeIf(task -> task.getId().equals(id));
    }
}
