package com.example.biletterservice.controller.dto.seats;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReleaseSeatRequest {
    @Positive
    @JsonProperty("seat_id")
    private long seatId;
}
