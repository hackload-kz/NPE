package com.example.biletterservice.service;

import com.example.biletterservice.dto.BookingDto;

import java.util.List;

public interface BookingService {
    Long createBooking(Long eventId);

    List<BookingDto> fetchUserBooking(Long userId);

    void initiatePayment(Long bookingId);

    void cancel(Long bookingId);


}
