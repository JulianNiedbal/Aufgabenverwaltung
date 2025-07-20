package com.example.aufgabenverwaltung.model.dto;

public class UserInsertionDto {

    private String username;

    public UserInsertionDto() {
    }

    public UserInsertionDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
