package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.focusSession.FocusSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FocusSessionUseCase {

    record CreateFocusSessionData(UUID userId, UUID subjectId, UUID activityId) {}
    record UpdateFocusSessionData(Integer durationSeconds, Boolean isCompleted) {}
    record FocusSessionQueryData(UUID userId, UUID subjectId, UUID activityId, Boolean isCompleted) {}
    record FocusSessionTickResult(int currentXp, int currentLevel, boolean leveledUp) {}

    FocusSession createFocusSession(CreateFocusSessionData data);
    FocusSession updateFocusSession(UUID id, UpdateFocusSessionData data);
    Optional<FocusSession> findFocusSessionById(UUID id);
    Page<FocusSession> findByQuery(FocusSessionQueryData query, Pageable pageable);
    void deleteFocusSession(UUID id);
    FocusSessionTickResult awardTickXp(UUID userId);
}
