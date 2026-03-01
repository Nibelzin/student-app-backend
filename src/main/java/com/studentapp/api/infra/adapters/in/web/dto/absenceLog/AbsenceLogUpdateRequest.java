package com.studentapp.api.infra.adapters.in.web.dto.absenceLog;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AbsenceLogUpdateRequest {

    private LocalDate absenceDate;
    private String notes;

}