package com.example.biletterservice.service;

import com.example.biletterservice.client.SeatClient;
import com.example.biletterservice.client.dto.SeatDto;
import com.example.biletterservice.repository.SeatRepository;
import com.example.biletterservice.repository.domain.SeatEntity;
import com.example.biletterservice.repository.domain.enumeration.SeatStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServicee {

    private final SeatRepository seatRepository;
    private final SeatClient seatClient;
    @Value("${batch.size}")
    private int PAGE_SIZE;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void initSeatsOnStartup() {
        int page = 1;
        while (true) {
            List<SeatDto> dtos = seatClient.getAllSeats(page, PAGE_SIZE);
            if (dtos == null || dtos.isEmpty()) break;

            List<SeatEntity> batch = new ArrayList<>(dtos.size());
            batch.addAll(dtos.parallelStream()
                    .map(this::convertToEntity)
                    .toList()
            );


            saveBatch(batch);

            if (dtos.size() < PAGE_SIZE) break;
            page++;
        }
        log.info("!!!!!!!! SEATS INITIALIZED SUCCESSFULLY !!!!!!!!");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBatch(List<SeatEntity> batch) {
        seatRepository.saveAll(batch);
    }

    private SeatEntity convertToEntity(SeatDto dto) {
        return new SeatEntity()
                .setNumber(dto.getSeat())
                .setRow(dto.getRow())
                .setInternalId(dto.getId())
                .setStatus(Boolean.TRUE.equals(dto.getIsFree())
                        ? SeatStatus.FREE : SeatStatus.RESERVED);
    }
}
