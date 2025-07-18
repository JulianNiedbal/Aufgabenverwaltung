package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.Task;
import com.example.aufgabenverwaltung.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("postgres")
public class PostgresTaskService implements TaskService {

    TaskRepository taskRepository;

    public PostgresTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTasks(String username) {
        return taskRepository.findAllByOwnerUsername(username);
    }

    @Override
    public Optional<Task> getTaskById(String username, Long id) {
        return taskRepository.findByIdAndOwnerUsername(id, username);
    }

    @Override
    public Task createTask(String username, Task task) {
        task.setOwnerUsername(username);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> updateTask(String username, Long id, Task updatedTask) {
        return taskRepository.findByIdAndOwnerUsername(id, username).map(
                existingTask -> {
                    existingTask.setTitle(updatedTask.getTitle());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(existingTask);
                }
        );
    }

    @Override
    @Transactional
    public boolean deleteTask(String username, Long id) {
        return taskRepository.deleteTaskByIdAndOwnerUsername(id, username) > 0;
    }
}
