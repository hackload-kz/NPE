package com.example.biletterservice.service;

import com.example.biletterservice.controller.dto.booking.Booking;
import com.example.biletterservice.controller.util.Converter;
import com.example.biletterservice.repository.BookingRepository;
import com.example.biletterservice.repository.EventRepository;
import com.example.biletterservice.repository.UserRepository;
import com.example.biletterservice.repository.domain.BookingEntity;
import com.example.biletterservice.repository.domain.UserEntity;
import com.example.biletterservice.repository.domain.enumeration.BookingStatus;
import com.example.biletterservice.security.UserAuthentication;
import com.example.biletterservice.service.exception.UserValidationException;
import com.example.biletterservice.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

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
        String lockKey = "BOOKING_LOCK_" + bookingId;
        ReentrantLock lock = locks.computeIfAbsent(lockKey, k -> new ReentrantLock());
        lock.lock();
        try {
            var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ValidationException(String.format("Booking with id [%d] does not exist", bookingId)));

            if (!validateBookingStatus(booking.getStatus(), BookingStatus.CREATED)) {
                throw new ValidationException(String.format("Booking with id [%d] in wrong state, expected: '%s'", bookingId, BookingStatus.CREATED.name()));
            }

            UserEntity bookingUser = booking.getUser();
            var bookingUserId = bookingUser.getId();
            var userId = getUserId();

            if (!validateUserId(userId, bookingUserId)) {
                throw new UserValidationException(String.format("Not equals current user id [%d] and booking user id: [%d]", userId, bookingUserId));
            }

            booking.setStatus(BookingStatus.PAYMENT_INITIATED);
            bookingRepository.save(booking);
        } finally {
            lock.unlock();
            locks.remove(lockKey, lock);
        }
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

    private boolean validateUserId(long userId, long expectedUserId) {
        return userId == expectedUserId;
    }
}
