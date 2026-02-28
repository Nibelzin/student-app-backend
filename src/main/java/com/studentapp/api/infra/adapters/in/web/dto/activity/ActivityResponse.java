package com.studentapp.api.infra.adapters.in.web.dto.activity;

import com.studentapp.api.domain.model.ChecklistItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ActivityResponse {

    private UUID id;
    private String title;
    private String description;
    private String type;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    private UUID subjectId;
    private String subjectName;
    private List<ChecklistItem> checklist;

}
