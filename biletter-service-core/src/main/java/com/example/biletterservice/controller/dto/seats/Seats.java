package com.example.biletterservice.controller.dto.seats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Seats {
    @JsonProperty("id")
    private Long id;
}
