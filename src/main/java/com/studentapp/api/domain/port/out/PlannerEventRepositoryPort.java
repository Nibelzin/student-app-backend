package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.PlannerEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PlannerEventRepositoryPort {
    PlannerEvent save(PlannerEvent plannerEvent);
    PlannerEvent update(PlannerEvent plannerEvent);
    Optional<PlannerEvent> findById(UUID id);
    Page<PlannerEvent> findByUserId(UUID userId, Pageable pageable);
    void delete(UUID id);
}
