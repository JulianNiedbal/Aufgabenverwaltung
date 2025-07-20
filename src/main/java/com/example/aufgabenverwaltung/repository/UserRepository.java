package com.example.aufgabenverwaltung.repository;

import com.example.aufgabenverwaltung.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    double deleteUserByUsername(String username);

    Optional<User> findByUsername(String username);
}
