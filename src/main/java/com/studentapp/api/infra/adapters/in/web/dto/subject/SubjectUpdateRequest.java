package com.studentapp.api.infra.adapters.in.web.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SubjectUpdateRequest {

    private String name;
    private String professor;
    private String classroom;
    private String color;

    @NotNull
    private UUID periodId;

}
