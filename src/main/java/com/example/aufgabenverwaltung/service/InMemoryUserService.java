package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.User;
import com.example.aufgabenverwaltung.model.mapper.UserMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("inMemory")
public class InMemoryUserService implements UserService {

    private final AtomicLong idGenerator = new AtomicLong();
    ArrayList<User> users = new ArrayList<>();

    InMemoryTaskService taskService = new InMemoryTaskService();

    public InMemoryUserService() {
    }

    @Override
    public User createUser(UserInsertionDto userInsertionDto) {
        User user = UserMapper.toEntity(userInsertionDto);
        if (users.stream().noneMatch(u -> u.getUsername().equals(userInsertionDto.getUsername()))) {
            user.setId(idGenerator.incrementAndGet());
            users.add(user);
        }
        return user;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<User> updateUser(String username, UserInsertionDto userInsertionDto) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                User user = UserMapper.toEntity(userInsertionDto);
                user.setId(users.get(i).getId());
                users.set(i, user);

                return Optional.of(users.get(i));
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUsername().equals(username)) {
                    users.remove(i);
                }
            }
            return true;
        }
        return false;
    }
}
