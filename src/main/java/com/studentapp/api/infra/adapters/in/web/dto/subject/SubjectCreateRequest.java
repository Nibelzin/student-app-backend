package com.studentapp.api.infra.adapters.in.web.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SubjectCreateRequest {


    @NotBlank(message = "o nome é obrigatório.")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres.")
    private String name;
    private String professor;
    private String classroom;
    private String color;
    private Integer maxAbsencesAllowed;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID periodId;

}
