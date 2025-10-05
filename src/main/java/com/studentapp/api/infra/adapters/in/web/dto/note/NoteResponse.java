package com.studentapp.api.infra.adapters.in.web.dto.note;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NoteResponse {

    private UUID id;
    private String content;
    private Boolean isPinned;
    private UUID userId;

}
