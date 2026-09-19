package com.parkingo.dto;

import com.parkingo.model.RequestStatus;
import com.parkingo.model.ValetRequest;
import java.time.Instant;
import java.util.UUID;

public record ValetRequestResponseDto(
        UUID id,
        UUID customerId,
        UUID valetId,
        UUID vehicleId,
        double pickupLat,
        double pickupLng,
        String pickupAddress,
        Instant scheduledFor,
        RequestStatus status,
        Integer priceCents,
        Instant createdAt
) {
    public static ValetRequestResponseDto from(ValetRequest r) {
        return new ValetRequestResponseDto(
                r.getId(), r.getCustomerId(), r.getValetId(), r.getVehicleId(),
                r.getPickupLat(), r.getPickupLng(), r.getPickupAddress(),
                r.getScheduledFor(), r.getStatus(), r.getPriceCents(), r.getCreatedAt()
        );
    }
}
