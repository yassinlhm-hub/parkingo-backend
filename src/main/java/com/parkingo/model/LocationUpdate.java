package com.parkingo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "location_updates")
public class LocationUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id", nullable = false)
    private UUID requestId;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt = Instant.now();

    protected LocationUpdate() {}

    public LocationUpdate(UUID requestId, double lat, double lng) {
        this.requestId = requestId;
        this.lat = lat;
        this.lng = lng;
    }

    public Long getId() { return id; }
    public UUID getRequestId() { return requestId; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public Instant getRecordedAt() { return recordedAt; }
}
