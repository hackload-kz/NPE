package com.example.biletterservice.client;

import com.example.biletterservice.client.dto.SeatDto;
import com.example.biletterservice.controller.dto.seats.Seats;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seat-client", url = "${service-provider.url}")
public interface SeatClient {
    @GetMapping("/api/partners/v1/places")
    List<Seats> getAllSeats(@RequestParam Integer page, @RequestParam Integer pageSize);
}
