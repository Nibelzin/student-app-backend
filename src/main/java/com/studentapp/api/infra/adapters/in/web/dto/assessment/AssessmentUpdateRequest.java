package com.studentapp.api.infra.adapters.in.web.dto.assessment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AssessmentUpdateRequest {

    private String title;
    private LocalDate assessmentDate;
    private Double grade;
    private Double maxGrade;
    private Double weight;
}
