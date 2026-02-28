package com.studentapp.api.infra.adapters.in.web.dto.material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
public class MaterialUpdateRequest {

    private String title;
    private String type;
    private Boolean isFavorite;

    private UUID subjectId;
    private UUID activityId;

    private String externalUrl;

    private MultipartFile file;

}
