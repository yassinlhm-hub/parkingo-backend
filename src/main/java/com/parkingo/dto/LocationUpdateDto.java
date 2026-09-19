package com.parkingo.dto;

import jakarta.validation.constraints.NotNull;

public record LocationUpdateDto(
        @NotNull Double lat,
        @NotNull Double lng
) {}
