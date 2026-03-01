package com.studentapp.api.infra.adapters.in.web.dto.absenceLog;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AbsenceLogCreateRequest {

    @NotNull
    private LocalDate absenceDate;

    private String notes;

    @NotNull
    private UUID subjectId;

}