package com.example.biletterservice.controller.dto;

import lombok.Data;

@Data
public class SuccessfulResponse implements Response {
    private final Status status = Status.SUCCESS;
}
