package com.studentapp.api.infra.adapters.in.web.dto.material;

import com.studentapp.api.domain.model.FileObject;
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
    private String fileUrl;
    private String externalUrl;
    private Boolean isFavorite;
    private LocalDateTime createdAt;

    private UUID subjectId;
    private String subjectName;

    private UUID activityId;
    private String activityName;


}
