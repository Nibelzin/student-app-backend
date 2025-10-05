package com.studentapp.api.infra.adapters.in.web.dto.period;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PeriodCreateRequest {

    @NotBlank(message = "o nome é obrigatório.")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres.")
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isCurrent = false;

    private UUID userId;

}
