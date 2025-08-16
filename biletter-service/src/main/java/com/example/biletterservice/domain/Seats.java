package com.example.biletterservice.domain;

import com.example.biletterservice.domain.enumeration.SeatsStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seats_seq", allocationSize = 50, sequenceName = "seats_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "row")
    private Integer row;
    @Column(name = "number")
    private Integer number;
    @Column(name = "status")
    private SeatsStatus status;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Long getId() {
        return id;
    }

    public Seats setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRow() {
        return row;
    }

    public Seats setRow(Integer row) {
        this.row = row;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public Seats setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public SeatsStatus getStatus() {
        return status;
    }

    public Seats setStatus(SeatsStatus status) {
        this.status = status;
        return this;
    }

    public Booking getBooking() {
        return booking;
    }

    public Seats setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }
}
