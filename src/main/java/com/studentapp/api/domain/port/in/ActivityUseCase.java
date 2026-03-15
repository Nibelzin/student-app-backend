package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.activity.Activity;
import com.studentapp.api.domain.model.activity.ChecklistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityUseCase {

    public record CreateActivityData(
            String title,
            String description,
            LocalDateTime dueDate,
            String type,
            Optional<LocalDateTime> reminderAt,
            UUID subjectId,
            List<ChecklistItem> checklist
    ) {}

    public record UpdateActivityData(
            String title,
            String description,
            LocalDateTime dueDate,
            Boolean isCompleted,
            String type,
            Optional<LocalDateTime> reminderAt,
            List<ChecklistItem> checklist
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
