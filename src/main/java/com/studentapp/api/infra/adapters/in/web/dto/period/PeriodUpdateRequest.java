package com.studentapp.api.infra.adapters.in.web.dto.period;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PeriodUpdateRequest {

    private String name;

    @NotNull(message = "data de início é obrigatório.")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NotNull(message = "data de conclusão é obrigatório")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

}
