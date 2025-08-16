package com.example.biletterservice.controller.util;

import com.example.biletterservice.controller.dto.booking.Booking;
import com.example.biletterservice.controller.dto.seats.Seats;
import com.example.biletterservice.repository.domain.BookingEntity;
import com.example.biletterservice.repository.domain.SeatEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Converter {
    public static Booking convertBooking(BookingEntity booking) {
        List<Seats> seats = booking.getSeats().stream().map(Converter::convertSeat).toList();
        return new Booking()
                .setId(booking.getId())
                .setEventId(booking.getEvent().getId())
                .setSeats(seats);
    }

    public static Seats convertSeat(SeatEntity entity) {
        return new Seats()
                .setId(entity.getId());
    }
}
