package com.parkingo.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateVehicleRequest(
        @NotBlank String plate,
        String brand,
        String model,
        String color
) {}
