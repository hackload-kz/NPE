package com.example.biletterservice.dto;

import java.util.List;

public class BookingDto {
    private Long id;
    private Long eventId;
    private List<SeatsDto> seats;

    public Long getId() {
        return id;
    }

    public BookingDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEventId() {
        return eventId;
    }

    public BookingDto setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public List<SeatsDto> getSeats() {
        return seats;
    }

    public BookingDto setSeats(List<SeatsDto> seats) {
        this.seats = seats;
        return this;
    }
}
