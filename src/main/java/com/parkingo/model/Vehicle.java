package com.parkingo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(nullable = false, length = 20)
    private String plate;

    private String brand;
    private String model;
    private String color;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @JsonIgnore
    @Column(nullable = false)
    private boolean deleted = false;

    protected Vehicle() {}

    public Vehicle(UUID ownerId, String plate, String brand, String model, String color) {
        this.ownerId = ownerId;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public String getPlate() { return plate; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getColor() { return color; }

    @JsonIgnore
    public boolean isDeleted() { return deleted; }
    public void markDeleted() { this.deleted = true; }
}
