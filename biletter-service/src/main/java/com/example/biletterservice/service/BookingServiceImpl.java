package com.example.biletterservice.service;

import com.example.biletterservice.domain.Booking;
import com.example.biletterservice.domain.enumeration.BookingStatus;
import com.example.biletterservice.dto.BookingDto;
import com.example.biletterservice.dto.DtoMapper;
import com.example.biletterservice.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Long createBooking(Long eventId) {
        Booking booking = new Booking()
                .setEventId(eventId)
                .setStatus(BookingStatus.CREATED);
        return bookingRepository.save(booking).getId();
    }

    @Override
    public List<BookingDto> fetchUserBooking(Long userId) {
        List<Booking> allByUserId = bookingRepository.findAllByUserId(userId);
        return allByUserId.stream().map(booking -> DtoMapper.toBookingDto(booking, new BookingDto())).toList();
    }

    @Override
    public void initiatePayment(Long bookingId) {
        Booking byId = bookingRepository.findById(bookingId).orElseThrow(RuntimeException::new);
        byId.setStatus(BookingStatus.PAYMENT_INITIATED);
        bookingRepository.save(byId);
    }

    @Override
    public void cancel(Long bookingId) {
        Booking byId = bookingRepository.findById(bookingId).orElseThrow(RuntimeException::new);
        byId.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(byId);

    }
}
