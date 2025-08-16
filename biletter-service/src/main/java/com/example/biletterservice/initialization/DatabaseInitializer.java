package com.example.biletterservice.initialization;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws IOException {
        String users = Files.readString(new ClassPathResource("users.sql").getFile().toPath());
        String events = Files.readString(new ClassPathResource("events.sql").getFile().toPath());

        CompletableFuture.runAsync(() -> {
            jdbcTemplate.execute(users);
            jdbcTemplate.execute(events);
        });
    }

}
