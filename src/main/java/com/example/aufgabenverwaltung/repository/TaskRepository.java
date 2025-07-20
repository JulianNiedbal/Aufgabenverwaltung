package com.example.aufgabenverwaltung.repository;

import com.example.aufgabenverwaltung.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByOwnerUsername(String ownerUsername);

    Optional<Task> findByIdAndOwnerUsername(Long id, String ownerUsername);

    int deleteTaskByIdAndOwnerUsername(Long id, String ownerUsername);

    void deleteAllByOwnerUsername(String username);
}
