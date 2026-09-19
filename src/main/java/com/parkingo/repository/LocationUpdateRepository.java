package com.parkingo.repository;

import com.parkingo.model.LocationUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LocationUpdateRepository extends JpaRepository<LocationUpdate, Long> {
    List<LocationUpdate> findByRequestIdOrderByRecordedAtDesc(UUID requestId);
}
