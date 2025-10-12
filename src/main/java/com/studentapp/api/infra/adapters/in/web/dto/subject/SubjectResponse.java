package com.studentapp.api.infra.adapters.in.web.dto.subject;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SubjectResponse {

    private UUID id;
    private String name;
    private String professor;
    private String classroom;
    private String color;
    private LocalDateTime createdAt;

    private UUID userId;
    private UUID periodId;

}
