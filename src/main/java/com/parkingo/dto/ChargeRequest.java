package com.parkingo.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ChargeRequest(
        @NotNull UUID requestId,
        @NotNull Integer amountCents
) {}
