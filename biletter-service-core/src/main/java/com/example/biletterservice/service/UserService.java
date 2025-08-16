package com.example.biletterservice.service;

import com.example.biletterservice.repository.UserRepository;
import com.example.biletterservice.repository.domain.UserEntity;
import com.example.biletterservice.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Nullable
    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapUser)
                .orElse(null);
    }


    private UserDto mapUser(UserEntity user) {
        return new UserDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPasswordHash(user.getPasswordHash())
                .setPasswordPlain(user.getPasswordPlain())
                .setFirstName(user.getFirstName())
                .setSurName(user.getSurName())
                .setBirthDate(user.getBirthDate())
                .setRegistrationDate(user.getRegistrationDate())
                .setActive(user.isActive())
                .setLastLoginTime(user.getLastLoginTime());
    }
}
