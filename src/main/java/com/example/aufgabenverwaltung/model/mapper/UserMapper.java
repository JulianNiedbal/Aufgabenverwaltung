package com.example.aufgabenverwaltung.model.mapper;

import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.User;

public class UserMapper {
    public static User toEntity(UserInsertionDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        return user;
    }
}
