package com.example.biletterservice.controller.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookingActionRequest {
    @Positive
    @JsonProperty("booking_id")
    private long bookingId;
}
