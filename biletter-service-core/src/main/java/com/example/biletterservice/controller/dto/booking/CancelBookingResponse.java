package com.example.biletterservice.controller.dto.booking;

import com.example.biletterservice.controller.dto.SuccessfulResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CancelBookingResponse extends SuccessfulResponse {
    @JsonProperty("booking_id")
    private long bookingId;
}
