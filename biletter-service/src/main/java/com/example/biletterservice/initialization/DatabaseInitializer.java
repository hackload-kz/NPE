package com.example.biletterservice.initialization;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws IOException {
        String sql = Files.readString(new ClassPathResource("users.sql").getFile().toPath());
        jdbcTemplate.execute(sql);
    }
}
