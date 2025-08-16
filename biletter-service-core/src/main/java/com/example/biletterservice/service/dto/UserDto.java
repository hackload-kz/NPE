package com.example.biletterservice.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserDto {
    private long id;
    private String email;
    private String passwordHash;
    private String passwordPlain;
    private String firstName;
    private String surName;
    private LocalDate birthDate;
    private LocalDateTime registrationDate;
    private boolean active;
    private LocalDateTime lastLoginTime;
}
