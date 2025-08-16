package com.example.biletterservice.service;

import com.example.biletterservice.client.SeatClient;
import com.example.biletterservice.controller.dto.seats.Seat;
import com.example.biletterservice.repository.SeatRepository;
import com.example.biletterservice.repository.domain.SeatEntity;
import com.example.biletterservice.repository.domain.enumeration.SeatStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatClient seatClient;
    @Value("${batch.size}")
    private int pageBatchSize;

    @PostConstruct
    public void initSeatsOnStartup() {
        int page = 1;
        if (seatRepository.count() > 0) {
            log.debug("Delete all seats");
            seatRepository.deleteAll();
        }

        while (true) {
            List<Seat> dtos = seatClient.getAllSeats(page, pageBatchSize);
            if (dtos == null || dtos.isEmpty()) break;

            List<SeatEntity> batch = new ArrayList<>(dtos.size());
            batch.addAll(dtos.parallelStream()
                    .map(this::convertToEntity)
                    .toList()
            );


            seatRepository.saveAll(batch);

            if (dtos.size() < pageBatchSize) break;
            page++;
        }
    }

    private SeatEntity convertToEntity(Seat dto) {
        return new SeatEntity()
                .setNumber(dto.getSeat())
                .setRow(dto.getRow())
                .setExternalId(dto.getId())
                .setStatus(Boolean.TRUE.equals(dto.getIsFree())
                        ? SeatStatus.FREE : SeatStatus.RESERVED);
    }
}
