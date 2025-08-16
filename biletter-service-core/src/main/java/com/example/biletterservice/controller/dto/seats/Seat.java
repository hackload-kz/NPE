package com.example.biletterservice.controller.dto.seats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Seat {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("row")
    private Integer row;
    @JsonProperty("seat")
    private Integer seat;
    @JsonProperty("is_free")
    private Boolean isFree;
}
