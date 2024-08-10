package com.tinqinacademy.bff.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final ObjectMapper objectMapper;

    public JwtToken decodeHeader(String jwtHeader) {
        String[] parts = jwtHeader.substring(7).split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        Map<String, String> claims;
        try {
            claims = objectMapper.readValue(payload, Map.class);
        }catch (Exception e){
            throw new RuntimeException("Invalid JWT token");
        }

        String id = claims.get("sub");
        String role = claims.get("role");

        return new JwtToken(id, role);
    }


}
