package com.example.biletterservice.service;

import com.example.biletterservice.controller.dto.booking.Booking;
import com.example.biletterservice.controller.util.Converter;
import com.example.biletterservice.repository.BookingRepository;
import com.example.biletterservice.repository.EventRepository;
import com.example.biletterservice.repository.domain.BookingEntity;
import com.example.biletterservice.repository.domain.UserEntity;
import com.example.biletterservice.repository.domain.enumeration.BookingStatus;
import com.example.biletterservice.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public Long createBooking(long eventId) {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new ValidationException(String.format("Event with id [%d] does not exist", eventId))); // todo add ExceptionHandler
        var user = new UserEntity(); // todo get user from SecurityContext

        var booking = new BookingEntity()
                .setEvent(event)
                .setUser(user)
                .setStatus(BookingStatus.CREATED);
        return bookingRepository.save(booking).getId();
    }

    public List<Booking> getUserBookings(long userId) {
        var userBookings = bookingRepository.findAllByUserId(userId);
        return userBookings.stream().map(Converter::convertBooking).toList();
    }

    public void initiatePayment(Long bookingId) {
        var byId = bookingRepository.findById(bookingId).orElseThrow(RuntimeException::new);
        byId.setStatus(BookingStatus.PAYMENT_INITIATED);
        bookingRepository.save(byId);
    }

    public void cancel(long bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ValidationException(String.format("Booking with id [%d] does not exist", bookingId)));
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
    }
}
