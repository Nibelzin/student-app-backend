package com.studentapp.api.infra.adapters.in.web.dto.note;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NoteCreateRequest {

    private String content;
    private Boolean isPinned;

    @NotNull
    private UUID userId;

}
