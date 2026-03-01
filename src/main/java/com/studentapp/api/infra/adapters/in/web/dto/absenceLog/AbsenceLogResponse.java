package com.studentapp.api.infra.adapters.in.web.dto.absenceLog;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AbsenceLogResponse {

    private UUID id;
    private LocalDate absenceDate;
    private String notes;
    private LocalDateTime createdAt;
    private UUID subjectId;
    private String subjectName;

}