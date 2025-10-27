package com.studentapp.api.infra.adapters.in.web.dto.material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
public class MaterialCreateRequest {

    @NotBlank(message = "O titulo é obrigatório")
    private String title;

    private String type;

    private Boolean isFavorite;

    @NotNull(message = "O Id da matéria é obrigatório")
    private UUID subjectId;

    private String externalUrl;


}
