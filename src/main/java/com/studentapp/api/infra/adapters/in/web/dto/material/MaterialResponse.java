package com.studentapp.api.infra.adapters.in.web.dto.material;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MaterialResponse {

    private UUID id;
    private String title;
    private String type;
    private Boolean isFavorite;
    private LocalDateTime createdAt;

    private UUID subjectId;

}
