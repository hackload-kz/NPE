package com.example.biletterservice.service;

import com.example.biletterservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public boolean eventExists(long eventId) {
        return eventRepository.existsById(eventId);
    }
}
