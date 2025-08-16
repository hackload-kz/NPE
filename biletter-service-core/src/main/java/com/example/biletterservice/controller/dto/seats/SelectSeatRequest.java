package com.example.biletterservice.controller.dto.seats;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SelectSeatRequest {
    @Positive
    @JsonProperty("booking_id")
    private long bookingId;

    @Positive
    @JsonProperty("seat_id")
    private long seatId;
}
