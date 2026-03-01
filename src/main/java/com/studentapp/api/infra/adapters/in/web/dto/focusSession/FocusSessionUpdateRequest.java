package com.studentapp.api.infra.adapters.in.web.dto.focusSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FocusSessionUpdateRequest {

    private Integer durationSeconds;

    private Boolean isCompleted;
}
