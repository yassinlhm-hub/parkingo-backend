package com.parkingo.service;

import com.parkingo.dto.CreateValetRequestDto;
import com.parkingo.exception.ApiException;
import com.parkingo.model.LocationUpdate;
import com.parkingo.model.RequestStatus;
import com.parkingo.model.ValetRequest;
import com.parkingo.repository.LocationUpdateRepository;
import com.parkingo.repository.ValetRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ValetRequestService {

    private static final int BASE_PRICE_CENTS = 1200;

    private final ValetRequestRepository requestRepository;
    private final LocationUpdateRepository locationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ValetRequestService(ValetRequestRepository requestRepository,
                                LocationUpdateRepository locationRepository,
                                SimpMessagingTemplate messagingTemplate) {
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public ValetRequest create(UUID customerId, CreateValetRequestDto dto) {
        ValetRequest request = new ValetRequest(
                customerId, dto.vehicleId(), dto.pickupLat(), dto.pickupLng(),
                dto.pickupAddress(), dto.scheduledFor(), BASE_PRICE_CENTS
        );
        return requestRepository.save(request);
    }

    public List<ValetRequest> pendingForValets() {
        return requestRepository.findByStatusOrderByCreatedAtAsc(RequestStatus.REQUESTED);
    }

    public List<ValetRequest> historyForCustomer(UUID customerId) {
        return requestRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public List<ValetRequest> historyForValet(UUID valetId) {
        return requestRepository.findByValetIdOrderByCreatedAtDesc(valetId);
    }

    public ValetRequest accept(UUID requestId, UUID valetId) {
        ValetRequest request = getOrThrow(requestId);
        if (request.getStatus() != RequestStatus.REQUESTED) {
            throw new ApiException(HttpStatus.CONFLICT, "La solicitud ya no está disponible");
        }
        request.setValetId(valetId);
        request.setStatus(RequestStatus.ACCEPTED);
        return requestRepository.save(request);
    }

    public ValetRequest updateStatus(UUID requestId, UUID actingUserId, RequestStatus newStatus) {
        ValetRequest request = getOrThrow(requestId);

        boolean isParticipant = request.getCustomerId().equals(actingUserId)
                || actingUserId.equals(request.getValetId());
        if (!isParticipant) {
            throw new ApiException(HttpStatus.FORBIDDEN, "No participas en esta solicitud");
        }

        request.setStatus(newStatus);
        ValetRequest saved = requestRepository.save(request);

        messagingTemplate.convertAndSend("/topic/requests/" + requestId + "/status", saved.getStatus());
        return saved;
    }

    public LocationUpdate recordLocation(UUID requestId, UUID valetId, double lat, double lng) {
        ValetRequest request = getOrThrow(requestId);
        if (!valetId.equals(request.getValetId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "No eres el valet asignado a esta solicitud");
        }

        LocationUpdate update = locationRepository.save(new LocationUpdate(requestId, lat, lng));
        messagingTemplate.convertAndSend("/topic/requests/" + requestId + "/location", update);
        return update;
    }

    public List<LocationUpdate> locationHistory(UUID requestId) {
        return locationRepository.findByRequestIdOrderByRecordedAtDesc(requestId);
    }

    private ValetRequest getOrThrow(UUID id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Solicitud no encontrada"));
    }
}
