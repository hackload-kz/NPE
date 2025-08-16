package com.example.biletterservice.controller.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateBookingRequest {
    @Positive
    @JsonProperty("event_id")
    private long eventId;
}
