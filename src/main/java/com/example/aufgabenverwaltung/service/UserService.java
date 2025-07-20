package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.User;

import java.util.Optional;

public interface UserService {
    User createUser(UserInsertionDto userInsertionDto);

    Optional<User> getUserByUsername(String username);

    Optional<User> updateUser(String username, UserInsertionDto userInsertionDto);

    boolean deleteUserByUsername(String username);


}
