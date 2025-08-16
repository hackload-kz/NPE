package com.example.biletterservice.controller.dto;

import lombok.Data;

@Data
public class ErrorResponse implements Response {
    private final Status status = Status.FAIL;
}
