package com.example.biletterservice.controller.dto.booking;

import com.example.biletterservice.controller.dto.seats.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Booking {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("event_id")
    private Long eventId;
    @JsonProperty("seats")
    private List<Seat> seats;
}
