package com.example.biletterservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "biletter_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
