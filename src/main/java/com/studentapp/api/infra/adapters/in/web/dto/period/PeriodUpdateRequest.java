package com.studentapp.api.infra.adapters.in.web.dto.period;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PeriodUpdateRequest {

    @NotBlank(message = "o nome é obrigatório.")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres.")
    private String name;

    @NotBlank(message = "data de início é obrigatório.")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NotBlank(message = "data de conclusão é obrigatório")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    private Boolean isCurrent;

}
