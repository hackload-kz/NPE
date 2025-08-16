package com.example.biletterservice.repository.domain;

import com.example.biletterservice.repository.domain.enumeration.SeatStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "SEATS")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seatsSequence")
    @SequenceGenerator(name = "seatsSequence", sequenceName = "SEATS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "ROW")
    private Integer row;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_ID")
    private BookingEntity booking;
}
