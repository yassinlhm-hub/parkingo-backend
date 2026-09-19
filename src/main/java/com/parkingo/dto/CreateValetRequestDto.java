package com.parkingo.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record CreateValetRequestDto(
        @NotNull UUID vehicleId,
        @NotNull Double pickupLat,
        @NotNull Double pickupLng,
        String pickupAddress,
        @NotNull Instant scheduledFor
) {}
