package com.example.biletterservice.dto;

import com.example.biletterservice.domain.enumeration.SeatsStatus;

public class SeatsDto {
    private Long id;
    private Integer row;
    private Integer number;
    private SeatsStatus status;

    public Long getId() {
        return id;
    }

    public SeatsDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRow() {
        return row;
    }

    public SeatsDto setRow(Integer row) {
        this.row = row;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public SeatsDto setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public SeatsStatus getStatus() {
        return status;
    }

    public SeatsDto setStatus(SeatsStatus status) {
        this.status = status;
        return this;
    }
}
