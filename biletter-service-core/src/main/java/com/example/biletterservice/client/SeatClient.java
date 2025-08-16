package com.example.biletterservice.client;

import com.example.biletterservice.client.dto.SeatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seat-client", url = "https://hub.hackload.kz/event-provider/common")
public interface SeatClient {
    @GetMapping("/api/partners/v1/places")
    List<SeatDto> getAllSeats(@RequestParam Integer page, @RequestParam Integer pageSize);
}
