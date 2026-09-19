package com.parkingo.controller;

import com.parkingo.dto.*;
import com.parkingo.model.LocationUpdate;
import com.parkingo.model.ValetRequest;
import com.parkingo.service.ValetRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/requests")
public class ValetRequestController {

    private final ValetRequestService requestService;

    public ValetRequestController(ValetRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<ValetRequestResponseDto> create(@AuthenticationPrincipal UUID userId,
                                                            @Valid @RequestBody CreateValetRequestDto dto) {
        ValetRequest created = requestService.create(userId, dto);
        return ResponseEntity.ok(ValetRequestResponseDto.from(created));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ValetRequestResponseDto>> pending() {
        return ResponseEntity.ok(toDtoList(requestService.pendingForValets()));
    }

    @GetMapping("/history/customer")
    public ResponseEntity<List<ValetRequestResponseDto>> historyCustomer(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(toDtoList(requestService.historyForCustomer(userId)));
    }

    @GetMapping("/history/valet")
    public ResponseEntity<List<ValetRequestResponseDto>> historyValet(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(toDtoList(requestService.historyForValet(userId)));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<ValetRequestResponseDto> accept(@AuthenticationPrincipal UUID userId,
                                                            @PathVariable UUID id) {
        return ResponseEntity.ok(ValetRequestResponseDto.from(requestService.accept(id, userId)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ValetRequestResponseDto> updateStatus(@AuthenticationPrincipal UUID userId,
                                                                  @PathVariable UUID id,
                                                                  @Valid @RequestBody UpdateStatusRequest req) {
        return ResponseEntity.ok(ValetRequestResponseDto.from(
                requestService.updateStatus(id, userId, req.status())));
    }

    @PostMapping("/{id}/location")
    public ResponseEntity<LocationUpdate> postLocation(@AuthenticationPrincipal UUID userId,
                                                         @PathVariable UUID id,
                                                         @Valid @RequestBody LocationUpdateDto dto) {
        return ResponseEntity.ok(requestService.recordLocation(id, userId, dto.lat(), dto.lng()));
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<List<LocationUpdate>> locationHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(requestService.locationHistory(id));
    }

    private List<ValetRequestResponseDto> toDtoList(List<ValetRequest> list) {
        return list.stream().map(ValetRequestResponseDto::from).collect(Collectors.toList());
    }
}
