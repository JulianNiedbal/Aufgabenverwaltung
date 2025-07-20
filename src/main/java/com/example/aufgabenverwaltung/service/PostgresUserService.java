package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.User;
import com.example.aufgabenverwaltung.model.mapper.UserMapper;
import com.example.aufgabenverwaltung.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("postgres")
public class PostgresUserService {

    UserRepository userRepository;

    public PostgresUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User createUser(UserInsertionDto userInsertionDto) {
        return userRepository.save(UserMapper.toEntity(userInsertionDto));
    }

    Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    Optional<User> updateUser(String username, UserInsertionDto userInsertionDto) {
        return userRepository.findByUsername(username).map(user -> userRepository.save(UserMapper.toEntity(userInsertionDto)));
    }

    boolean deleteUserByUsername(String username) {
        return userRepository.deleteUserByUsername(username) > 0;
    }

}
