package com.example.aufgabenverwaltung.service;

import com.example.aufgabenverwaltung.model.dto.UserInsertionDto;
import com.example.aufgabenverwaltung.model.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InMemoryUserServiceTests {

    private InMemoryUserService userService;

    @BeforeEach
    void setUp() {
        userService = new InMemoryUserService();
    }

    @Test
    void getUser_ShouldReturnUserIfItExits() {
        UserInsertionDto userInsertionDto = new UserInsertionDto("Test User");
        userService.createUser(userInsertionDto);
        Optional<User> createdUser = userService.getUserByUsername(userInsertionDto.getUsername());

        assertTrue(createdUser.isPresent(), "User wurde nicht erstellt");
        assertEquals(createdUser.get().getUsername(), userInsertionDto.getUsername(), "Es wurde nicht der richtige User geladen");
    }

    @Test
    void getUser_ShouldNotReturnUserIfItDoesNotExit() {
        userService.deleteUserByUsername("Test User");
        Optional<User> user = userService.getUserByUsername("Test User");

        assertTrue(user.isEmpty(), "User wurde geladen, obwohl er nicht existieren sollte");
    }

    @Test
    void createUser_ShouldCreateUserIfNoUserWithSameUsernameExists() {
        String username = "Test User";
        userService.deleteUserByUsername(username);
        UserInsertionDto userInsertionDto = new UserInsertionDto(username);
        User createdUser = userService.createUser(userInsertionDto);

        assertNotNull(createdUser, "User wurde nicht erstellt");
    }

    @Test
    void createUser_ShouldNotCreateUserIfUserWithSameUsernameExists() {
        String username = "Test User";
        userService.deleteUserByUsername(username);
        UserInsertionDto userInsertionDto = new UserInsertionDto(username);
        userService.createUser(userInsertionDto);
        User createdUser = userService.createUser(userInsertionDto);


        assertNull(createdUser.getId(), "User wurde erstellt");
    }

    @Test
    void updateUser_ShouldUpdateUserIfUserExists() {
        String oldUsername = "Old User";
        String newUsername = "New User";
        userService.deleteUserByUsername(oldUsername);
        userService.deleteUserByUsername(newUsername);
        userService.createUser(new UserInsertionDto(oldUsername));
        Optional<User> user = userService.updateUser(oldUsername, new UserInsertionDto(newUsername));

        assertTrue(user.isPresent(), "User wurde nicht erstellt");
        assertNotEquals(oldUsername, user.get().getUsername(), "User wurde nicht geupdated");
    }

    @Test
    void updateUser_ShouldNotUpdateUserIfUserDoesNotExist() {
        String oldUsername = "Old User";
        String newUsername = "New User";
        userService.deleteUserByUsername(oldUsername);
        Optional<User> user = userService.updateUser(oldUsername, new UserInsertionDto(newUsername));

        assertTrue(user.isEmpty(), "User wurde erstellt");
    }

    @Test
    void deleteUserByUsername_ShouldDeleteUserIfUserExists() {
        String username = "Test User";
        userService.createUser(new UserInsertionDto(username));
        boolean deleted = userService.deleteUserByUsername(username);
        Optional<User> user = userService.getUserByUsername(username);

        assertTrue(user.isEmpty(), "User wurde nicht gelöscht");
        assertTrue(deleted, "User wurde gelöscht, aber es wurde True zurückgegeben");
    }

    @Test
    void deleteUser_ShouldNotDeleteUserIfUserDoesNotExist() {
        String username = "Test User";
        boolean deleted = userService.deleteUserByUsername(username);

        assertFalse(deleted, "User wurde gelöscht");
    }

}
