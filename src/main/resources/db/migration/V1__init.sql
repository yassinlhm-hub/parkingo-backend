CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    phone VARCHAR(30),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE vehicles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_id UUID NOT NULL REFERENCES users(id),
    plate VARCHAR(20) NOT NULL,
    brand VARCHAR(60),
    model VARCHAR(60),
    color VARCHAR(30),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE valet_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL REFERENCES users(id),
    valet_id UUID REFERENCES users(id),
    vehicle_id UUID NOT NULL REFERENCES vehicles(id),
    pickup_lat DOUBLE PRECISION NOT NULL,
    pickup_lng DOUBLE PRECISION NOT NULL,
    pickup_address VARCHAR(255),
    scheduled_for TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'REQUESTED',
    price_cents INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE location_updates (
    id BIGSERIAL PRIMARY KEY,
    request_id UUID NOT NULL REFERENCES valet_requests(id),
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    recorded_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    request_id UUID NOT NULL REFERENCES valet_requests(id) UNIQUE,
    amount_cents INTEGER NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'EUR',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    provider_reference VARCHAR(120),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_valet_requests_customer ON valet_requests(customer_id);
CREATE INDEX idx_valet_requests_valet ON valet_requests(valet_id);
CREATE INDEX idx_location_updates_request ON location_updates(request_id);
