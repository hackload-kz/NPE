package com.example.biletterservice.domain;

import com.example.biletterservice.domain.enumeration.BookingStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "booking_seq", sequenceName = "booking_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Seats> seats;

    @Column(name = "status")
    private BookingStatus status;

    private Long userId;

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEventId() {
        return eventId;
    }

    public Booking setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public List<Seats> getSeats() {
        return seats;
    }

    public Booking setSeats(List<Seats> seats) {
        this.seats = seats;
        return this;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Booking setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }
}
