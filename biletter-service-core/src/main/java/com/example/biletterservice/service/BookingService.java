package com.example.biletterservice.service;

import com.example.biletterservice.controller.dto.booking.Booking;
import com.example.biletterservice.controller.util.Converter;
import com.example.biletterservice.repository.BookingRepository;
import com.example.biletterservice.repository.EventRepository;
import com.example.biletterservice.repository.UserRepository;
import com.example.biletterservice.repository.domain.BookingEntity;
import com.example.biletterservice.repository.domain.enumeration.BookingStatus;
import com.example.biletterservice.security.UserAuthentication;
import com.example.biletterservice.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Long createBooking(long eventId) {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new ValidationException(String.format("Event with id [%d] does not exist", eventId)));
        var userId = getUserId();
        var user = userRepository.findById(userId).orElseThrow(() -> new ValidationException(String.format("User with id [%d] does not exist", userId)));

        var booking = new BookingEntity()
                .setEvent(event)
                .setUser(user)
                .setStatus(BookingStatus.CREATED);
        return bookingRepository.save(booking).getId();
    }

    public List<Booking> getUserBookings() {
        var userId = getUserId();
        var userBookings = bookingRepository.findAllByUserId(userId);
        return userBookings.stream().map(Converter::convertBooking).toList();
    }

    public void initiatePayment(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ValidationException(String.format("Booking with id [%d] does not exist", bookingId)));
        if (!validateBookingStatus(booking.getStatus(), BookingStatus.SEATS_CHOSEN)) {
            throw new ValidationException(String.format("Booking with id [%d] does not initiated. Booking on wrong status", bookingId));
        }
        booking.setStatus(BookingStatus.PAYMENT_INITIATED);
        bookingRepository.save(booking);
    }

    public void cancel(long bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ValidationException(String.format("Booking with id [%d] does not exist", bookingId)));
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
    }

    private long getUserId() {
        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }

    private boolean validateBookingStatus(BookingStatus current, BookingStatus expected) {
        return current == expected;
    }
}
