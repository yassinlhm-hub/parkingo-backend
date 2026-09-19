package com.parkingo.service;

import com.parkingo.dto.CreateVehicleRequest;
import com.parkingo.exception.ApiException;
import com.parkingo.model.Vehicle;
import com.parkingo.repository.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle create(UUID ownerId, CreateVehicleRequest req) {
        Vehicle vehicle = new Vehicle(ownerId, req.plate(), req.brand(), req.model(), req.color());
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> listForOwner(UUID ownerId) {
        return vehicleRepository.findByOwnerIdAndDeletedFalse(ownerId);
    }

    /** Borrado lógico: los servicios ya realizados con este vehículo conservan su referencia. */
    public void delete(UUID ownerId, UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findByIdAndOwnerIdAndDeletedFalse(vehicleId, ownerId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Vehículo no encontrado"));
        vehicle.markDeleted();
        vehicleRepository.save(vehicle);
    }
}
