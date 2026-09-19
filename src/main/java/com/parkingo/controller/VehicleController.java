package com.parkingo.controller;

import com.parkingo.dto.CreateVehicleRequest;
import com.parkingo.model.Vehicle;
import com.parkingo.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@AuthenticationPrincipal UUID userId,
                                           @Valid @RequestBody CreateVehicleRequest req) {
        return ResponseEntity.ok(vehicleService.create(userId, req));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<Vehicle>> mine(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(vehicleService.listForOwner(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UUID userId, @PathVariable UUID id) {
        vehicleService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}
