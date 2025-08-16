package com.example.biletterservice.controller;

import com.example.biletterservice.dto.BookingDto;
import com.example.biletterservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody Long eventId) {
        return ResponseEntity.ok().body(bookingService.createBooking(eventId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> fetchBookings(@RequestBody Long userId) {
        return ResponseEntity.ok().body(bookingService.fetchUserBooking(userId));
    }

    @PatchMapping("/initiatePayment ")
    public ResponseEntity<String> initiateBookingPayment(@RequestBody Long bookingId) {
        bookingService.initiatePayment(bookingId);
        return ResponseEntity.ok().body("Бронь ожидает подтверждения платежа");
    }

    @PatchMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestBody Long bookingId) {
        bookingService.cancel(bookingId);
        return ResponseEntity.ok().body("Бронь успешно отменена");
    }
}
