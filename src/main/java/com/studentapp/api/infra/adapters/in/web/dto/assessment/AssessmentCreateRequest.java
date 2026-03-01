package com.studentapp.api.infra.adapters.in.web.dto.assessment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AssessmentCreateRequest {

    @NotBlank
    private String title;

    @NotNull
    private LocalDate assessmentDate;

    private Double grade;
    private Double maxGrade;
    private Double weight;

    @NotNull
    private UUID subjectId;
}
