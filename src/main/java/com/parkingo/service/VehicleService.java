package com.parkingo.service;

import com.parkingo.dto.CreateVehicleRequest;
import com.parkingo.model.Vehicle;
import com.parkingo.repository.VehicleRepository;
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
        return vehicleRepository.findByOwnerId(ownerId);
    }
}
