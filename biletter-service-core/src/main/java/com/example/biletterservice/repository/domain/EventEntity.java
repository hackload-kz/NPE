package com.example.biletterservice.repository.domain;

import com.example.biletterservice.repository.domain.enumeration.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "EVENTS")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventsSequence")
    @SequenceGenerator(name = "eventsSequence", sequenceName = "EVENTS_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE", nullable = false)
    private EventType type;

    @Column(name = "DATETIME_START", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "PROVIDER", nullable = false)
    private String provider;
}
