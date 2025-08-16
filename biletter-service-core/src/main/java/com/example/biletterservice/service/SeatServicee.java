package com.example.biletterservice.service;

import com.example.biletterservice.client.SeatClient;
import com.example.biletterservice.client.dto.SeatDto;
import com.example.biletterservice.repository.SeatRepository;
import com.example.biletterservice.repository.domain.SeatEntity;
import com.example.biletterservice.repository.domain.enumeration.SeatStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class SeatServicee {

    private static final Logger log = LoggerFactory.getLogger(SeatServicee.class);
    private final SeatRepository seatRepository;
    private final SeatClient seatClient;

    @PostConstruct
    public void testSeats() {
        List<SeatDto> seatDtoList = seatClient.getAllSeats();
        int chunkSize = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<List<SeatDto>> chunks = new ArrayList<>();
        for (int i = 0; i < seatDtoList.size(); i += chunkSize) {
            chunks.add(seatDtoList.subList(i, Math.min(i + chunkSize, seatDtoList.size())));
        }
        List<CompletableFuture<Void>> futures = chunks.stream()
                .map(chunk -> CompletableFuture.runAsync(() -> processChunk(chunk), executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }

    private void processChunk(List<SeatDto> chunk) {
        try {
            Set<SeatEntity> batch = new HashSet<>();
            for (SeatDto dto : chunk) {
                SeatEntity seatEntity = new SeatEntity();

                //внутренний идентификатор места
                seatEntity.setInternalId(dto.getId());
                seatEntity.setRow(dto.getRow());
                seatEntity.setNumber(dto.getSeat());
                if (dto.getIsFree()) {
                    seatEntity.setStatus(SeatStatus.FREE);
                } else {
                    seatEntity.setStatus(SeatStatus.RESERVED);
                }

                batch.add(seatEntity);
            }

            seatRepository.saveAll(batch);

            log.info("Thread {}: saved {} seats", Thread.currentThread().getName(), batch.size());
        } catch (Exception e) {
            log.error("Error in thread {}: {}", Thread.currentThread().getName(), e.getMessage(), e);
        }
    }
}
