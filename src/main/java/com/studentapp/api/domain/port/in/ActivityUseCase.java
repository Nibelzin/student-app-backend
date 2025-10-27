package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ActivityUseCase {

    public record CreateActivityData(
            String title,
            String description,
            LocalDateTime dueDate,
            String type,
            Optional<LocalDateTime> reminderAt,
            UUID subjectId
    ) {}

    public record UpdateActivityData(
            String title,
            String description,
            LocalDateTime dueDate,
            Boolean isCompleted,
            String type,
            Optional<LocalDateTime> reminderAt
    ) {}

    public record ActivityQueryData(
            Optional<UUID> subjectId,
            Optional<UUID> userId,
            Optional<String> type,
            Optional<LocalDateTime> dueDate,
            Optional<Boolean> isCompleted,
            Optional<Boolean> isOverdue
    ) {}

    Activity createActivity(CreateActivityData createActivityData);
    Activity updateActivity(UUID id, UpdateActivityData updateActivityData);
    Optional<Activity> findActivityById(UUID id);
    Page<Activity> findActivities(ActivityQueryData query, Pageable pageable);

    void deleteActivity(UUID id);
}
