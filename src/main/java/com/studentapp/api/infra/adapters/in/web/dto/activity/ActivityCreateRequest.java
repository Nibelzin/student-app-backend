package com.studentapp.api.infra.adapters.in.web.dto.activity;

import com.studentapp.api.domain.model.ChecklistItem;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ActivityCreateRequest {

    @NotBlank(message = "O titulo é obrigatório")
    private String title;

    private String description;
    private LocalDateTime dueDate;
    private Boolean isCompleted;
    private String type;

    private UUID subjectId;
    private List<ChecklistItem> checklist;

}
