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
public class PostgresUserService implements UserService {

    UserRepository userRepository;

    public PostgresUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserInsertionDto userInsertionDto) {
        return userRepository.save(UserMapper.toEntity(userInsertionDto));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> updateUser(String username, UserInsertionDto userInsertionDto) {
        return userRepository.findByUsername(username)
                .map(oldUser -> {
                    oldUser.setUsername(userInsertionDto.getUsername());
                    return userRepository.save(oldUser);
                });
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        // Todo Testen
        return userRepository.deleteUserByUsername(username) > 0;
    }

}
