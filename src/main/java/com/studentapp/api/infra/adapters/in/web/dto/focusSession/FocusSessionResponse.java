package com.studentapp.api.infra.adapters.in.web.dto.focusSession;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FocusSessionResponse {

    private UUID id;
    private int durationSeconds;
    private Boolean isCompleted;
    private int xpEarned;
    private LocalDateTime createdAt;
    private UUID userId;
    private UUID subjectId;
    private String subjectName;
    private UUID activityId;
    private String activityName;
}
