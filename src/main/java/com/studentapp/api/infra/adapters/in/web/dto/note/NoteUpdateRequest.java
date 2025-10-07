package com.studentapp.api.infra.adapters.in.web.dto.note;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteUpdateRequest {

    private String content;
    private Boolean isPinned;

}
