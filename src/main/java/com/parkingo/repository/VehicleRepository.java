package com.parkingo.repository;

import com.parkingo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    List<Vehicle> findByOwnerIdAndDeletedFalse(UUID ownerId);
    Optional<Vehicle> findByIdAndOwnerIdAndDeletedFalse(UUID id, UUID ownerId);
}
