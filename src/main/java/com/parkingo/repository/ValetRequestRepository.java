package com.parkingo.repository;

import com.parkingo.model.RequestStatus;
import com.parkingo.model.ValetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ValetRequestRepository extends JpaRepository<ValetRequest, UUID> {
    List<ValetRequest> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
    List<ValetRequest> findByValetIdOrderByCreatedAtDesc(UUID valetId);
    List<ValetRequest> findByStatusOrderByCreatedAtAsc(RequestStatus status);
}
