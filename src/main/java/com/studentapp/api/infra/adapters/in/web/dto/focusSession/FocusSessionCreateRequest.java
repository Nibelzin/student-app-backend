package com.studentapp.api.infra.adapters.in.web.dto.focusSession;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FocusSessionCreateRequest {

    @Min(1)
    private int durationSeconds;

    private Boolean isCompleted;

    @NotNull
    private UUID userId;

    private UUID subjectId;

    private UUID activityId;
}
