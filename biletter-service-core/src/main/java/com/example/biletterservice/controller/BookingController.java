package com.example.biletterservice.controller;

import com.example.biletterservice.controller.dto.Response;
import com.example.biletterservice.controller.dto.booking.Booking;
import com.example.biletterservice.controller.dto.booking.BookingActionRequest;
import com.example.biletterservice.controller.dto.booking.CancelBookingResponse;
import com.example.biletterservice.controller.dto.booking.CreateBookingRequest;
import com.example.biletterservice.controller.dto.booking.CreateBookingResponse;
import com.example.biletterservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Response> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        long bookingId = bookingService.createBooking(request.getEventId());
        return ResponseEntity.ok().body(new CreateBookingResponse().setBookingId(bookingId));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getBookings() {
        long userId = 1L; // Todo get user id from SecurityContext
        return ResponseEntity.ok().body(bookingService.getUserBookings(userId));
    }

    @PatchMapping("/initiatePayment")
    public ResponseEntity<String> initiateBookingPayment(@Valid @RequestBody BookingActionRequest request) {
        bookingService.initiatePayment(request.getBookingId());
        return ResponseEntity.ok().body("Бронь ожидает подтверждения платежа"); // todo refactor, go to payment gate and initiate payment
    }

    @PatchMapping("/cancel")
    public ResponseEntity<Response> cancelBooking(@Valid @RequestBody BookingActionRequest request) {
        bookingService.cancel(request.getBookingId());
        return ResponseEntity.ok().body(new CancelBookingResponse()
                .setBookingId(request.getBookingId()));
    }
}
