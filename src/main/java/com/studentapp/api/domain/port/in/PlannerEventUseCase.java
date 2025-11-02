package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.PlannerEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PlannerEventUseCase {

    public record CreatePlannerEventData(
            String title,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Boolean allDay,
            String rule,
            String color,
            UUID userId,
            UUID activityId,
            UUID subjectId
    ){};

    public record UpdatePlannerEventData(
            Optional<String> title,
            Optional<LocalDateTime> startAt,
            Optional<LocalDateTime> endAt,
            Optional<Boolean> allDay,
            Optional<String> rule,
            Optional<String> color,
            Optional<UUID> activityId,
            Optional<UUID> subjectId
    ){};

    public PlannerEvent createPlannerEvent(CreatePlannerEventData createPlannerEventData);
    public PlannerEvent updatePlannerEvent(UUID id, UpdatePlannerEventData updatePlannerEventData);
    Optional<PlannerEvent> findPlannerEventById(UUID id);
    Page<PlannerEvent> findPlannerEventsByUserId(UUID userId, Pageable pageable);
    void deletePlannerEvent(UUID id);

}
