package com.example.biletterservice.repository.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "USERS")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSequence")
    @SequenceGenerator(name = "usersSequence", sequenceName = "USERS_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "PASSWORD_PLAIN")
    private String passwordPlain;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "SURNAME", nullable = false)
    private String surName;

    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;

    @Column(name = "REGISTERED_AT", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "LAST_LOGGED_IN", nullable = false)
    private LocalDateTime lastLoginTime;
}
