package com.example.biletterservice.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserAuthentication implements Authentication {
    private final String email;
    private final long userId;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String toString() {
        return "ClientAuthentication{" +
                "email='" + email + '\'' +
                ", userId=" + userId +
                ", authorities=" + authorities +
                '}';
    }
}
