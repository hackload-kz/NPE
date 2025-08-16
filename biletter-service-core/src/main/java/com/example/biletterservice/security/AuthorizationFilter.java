package com.example.biletterservice.security;

import com.example.biletterservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Basic ")) {
            String base64Credentials = header.substring(6).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decoded = new String(decodedBytes, StandardCharsets.UTF_8);

            String[] parts = decoded.split(":", 2);
            if (parts.length == 2) {
                String username = parts[0];
                String password = parts[1];

                var user = userService.findByEmail(username);
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                if (!user.getPasswordPlain().equals(password)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user.getEmail(), user.getId()));
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
