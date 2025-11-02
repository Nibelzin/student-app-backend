package com.studentapp.api.infra.adapters.in.web.dto.plannerEvent;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PlannerEventCreateRequest {

    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean allDay;
    private String rule;
    private String color;

    @NotNull(message = "Usuário não pode ser nulo.")
    private UUID userId;

    private UUID subjectId;
    private UUID ActivityId;

}
