package com.example.biletterservice.controller;

import com.example.biletterservice.controller.dto.seats.ReleaseSeatRequest;
import com.example.biletterservice.controller.dto.seats.SelectSeatRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/seats")
@RequiredArgsConstructor
public class SeatsController {

    @PatchMapping("/select")
    public ResponseEntity<String> selectSeat(@Valid @RequestBody SelectSeatRequest request) {
        //todo. we need release after seats service realisation
        return ResponseEntity.ok().body("Место успешно добавлено в бронь");
    }

    @PatchMapping("/release")
    public ResponseEntity<String> releaseSeat(@Valid @RequestBody ReleaseSeatRequest request) {
        //todo. we need release after seats service realisation
        return ResponseEntity.ok().body("Место успешно освобождено");
    }

}
