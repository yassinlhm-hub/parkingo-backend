package com.parkingo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "request_id", nullable = false, unique = true)
    private UUID requestId;

    @Column(name = "amount_cents", nullable = false)
    private int amountCents;

    @Column(nullable = false, length = 3)
    private String currency = "EUR";

    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "provider_reference", length = 120)
    private String providerReference;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Payment() {}

    public Payment(UUID requestId, int amountCents, String providerReference) {
        this.requestId = requestId;
        this.amountCents = amountCents;
        this.providerReference = providerReference;
        this.status = "CONFIRMED";
    }

    public UUID getId() { return id; }
    public UUID getRequestId() { return requestId; }
    public int getAmountCents() { return amountCents; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public String getProviderReference() { return providerReference; }
    public Instant getCreatedAt() { return createdAt; }
}
