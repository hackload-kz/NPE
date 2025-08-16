package com.example.biletterservice.dto;

import com.example.biletterservice.domain.Booking;
import com.example.biletterservice.domain.Seats;

import java.util.List;

public class DtoMapper {
    private DtoMapper() {}
    public static BookingDto toBookingDto(Booking entity, BookingDto dto) {
        List<SeatsDto> seats = entity.getSeats().stream().map(e -> toSeatsDto(e, new SeatsDto())).toList();
        return new BookingDto()
                .setId(entity.getId())
                .setEventId(dto.getEventId())
                .setSeats(seats);
    }

    public static Booking toBooking(Booking entity, BookingDto dto) {
        List<Seats> seats = dto.getSeats().stream().map(d -> toSeats(new Seats(), d)).toList();
        return new Booking()
                .setId(dto.getId())
                .setEventId(dto.getEventId())
                .setSeats(seats);
    }

    public static SeatsDto toSeatsDto(Seats entity, SeatsDto dto) {
        return new SeatsDto()
                .setId(entity.getId())
                .setRow(entity.getRow())
                .setNumber(entity.getNumber())
                .setStatus(entity.getStatus());
    }

    public static Seats toSeats(Seats entity, SeatsDto dto) {
        return new Seats()
                .setId(dto.getId())
                .setRow(dto.getRow())
                .setNumber(dto.getNumber())
                .setStatus(dto.getStatus());
    }
}
