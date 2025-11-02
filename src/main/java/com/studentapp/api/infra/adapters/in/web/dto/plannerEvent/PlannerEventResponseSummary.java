package com.studentapp.api.infra.adapters.in.web.dto.plannerEvent;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PlannerEventResponseSummary {

    private UUID id;
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean allDay;
    private String rule;
    private String color;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UUID userId;
    private String userName;

    private UUID subjectId;
    private String subjectName;

    private UUID ActivityId;
    private String ActivityName;

}
