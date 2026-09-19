package com.parkingo.dto;

import com.parkingo.model.RequestStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull RequestStatus status
) {}
