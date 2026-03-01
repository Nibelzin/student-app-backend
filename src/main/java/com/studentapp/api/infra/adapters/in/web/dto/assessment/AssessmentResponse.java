package com.studentapp.api.infra.adapters.in.web.dto.assessment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AssessmentResponse {

    private UUID id;
    private String title;
    private LocalDate assessmentDate;
    private Double grade;
    private Double maxGrade;
    private Double weight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID subjectId;
    private String subjectName;
}
