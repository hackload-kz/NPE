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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SeatServicee {

    private static final Logger log = LoggerFactory.getLogger(SeatServicee.class);
    private final SeatRepository seatRepository;
    private final SeatClient seatClient;
    private static final int PAGE_SIZE = 1000;

    @PostConstruct
    public void testSeats() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        AtomicInteger totalSeats = new AtomicInteger(0);

        try {
            for (int i = 1; i <= 100; i++) {
                final int index = i;
                executor.submit(() -> {
                    try {
                        List<SeatDto> seats = seatClient.getAllSeats(index, 1000);
                        List<SeatEntity> entities = seats.stream()
                                .map(this::convertToEntity)
                                .toList();
                        seatRepository.saveAll(entities);
                        totalSeats.addAndGet(seats.size());
                        System.out.println("Поток " + Thread.currentThread().getName() +
                                " обработал index=" + index + ", записей: " + seats.size());
                    } catch (Exception e) {
                        System.err.println("Ошибка для index=" + index + ": " + e.getMessage());
                    }
                });
            }

            executor.shutdown();
            try {
                if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

            System.out.println("Всего записей обработано: " + totalSeats.get());
        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }
    }

    private SeatEntity convertToEntity(SeatDto dto) {
        SeatEntity seatEntity = new SeatEntity()
                .setNumber(dto.getSeat())
                .setRow(dto.getRow())
                .setInternalId(dto.getId());
        if (dto.getIsFree() != null && dto.getIsFree()) {
            seatEntity.setStatus(SeatStatus.FREE);
        } else {
            seatEntity.setStatus(SeatStatus.RESERVED);
        }
        return seatEntity;
    }

    /*@Async
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
    }*/
}
