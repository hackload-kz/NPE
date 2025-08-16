package com.example.biletterservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("row")
    private Integer row;
    @JsonProperty("seat")
    private Integer seat;
    @JsonProperty("is_free")
    private Boolean isFree;
}
