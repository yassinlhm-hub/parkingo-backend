package com.parkingo.dto;

import java.util.UUID;

public record AuthResponse(
        String token,
        UUID userId,
        String fullName,
        String role,
        String email
) {}
