package com.studentapp.api.infra.adapters.in.web.dto.focusSession;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FocusSessionCreateRequest {

    @NotNull
    private UUID userId;

    private UUID subjectId;

    private UUID activityId;
}
