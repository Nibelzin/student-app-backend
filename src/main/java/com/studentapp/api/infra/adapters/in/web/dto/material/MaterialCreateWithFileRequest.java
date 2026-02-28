package com.studentapp.api.infra.adapters.in.web.dto.material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
public class MaterialCreateWithFileRequest {

    @NotBlank(message = "O titulo é obrigatório")
    private String title;
    private String type;
    private Boolean isFavorite;

    private UUID subjectId;

    private UUID activityId;

    @NotNull(message = "o arquivo é obrigatório")
    private MultipartFile file;

}
