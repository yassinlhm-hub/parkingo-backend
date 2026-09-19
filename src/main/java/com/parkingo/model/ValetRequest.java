package com.parkingo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "valet_requests")
public class ValetRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "valet_id")
    private UUID valetId;

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Column(name = "pickup_lat", nullable = false)
    private double pickupLat;

    @Column(name = "pickup_lng", nullable = false)
    private double pickupLng;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "scheduled_for", nullable = false)
    private Instant scheduledFor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequestStatus status = RequestStatus.REQUESTED;

    @Column(name = "price_cents")
    private Integer priceCents;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    protected ValetRequest() {}

    public ValetRequest(UUID customerId, UUID vehicleId, double pickupLat, double pickupLng,
                         String pickupAddress, Instant scheduledFor, Integer priceCents) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.pickupLat = pickupLat;
        this.pickupLng = pickupLng;
        this.pickupAddress = pickupAddress;
        this.scheduledFor = scheduledFor;
        this.priceCents = priceCents;
    }

    public void touch() { this.updatedAt = Instant.now(); }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public UUID getValetId() { return valetId; }
    public void setValetId(UUID valetId) { this.valetId = valetId; touch(); }
    public UUID getVehicleId() { return vehicleId; }
    public double getPickupLat() { return pickupLat; }
    public double getPickupLng() { return pickupLng; }
    public String getPickupAddress() { return pickupAddress; }
    public Instant getScheduledFor() { return scheduledFor; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; touch(); }
    public Integer getPriceCents() { return priceCents; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
