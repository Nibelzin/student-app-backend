package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteResponse;
import org.springframework.stereotype.Component;

@Component
public class NoteDtoMapper {

    public Note toDomain(NoteCreateRequest noteCreateRequest, User user){
        return Note.create(noteCreateRequest.getContent(), noteCreateRequest.getIsPinned(), user);
    }

    public NoteResponse toResponse(Note note) {
        if (note == null) {
            return null;
        }

        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setContent(note.getContent());
        response.setIsPinned(note.isPinned());
        response.setUserId(note.getUser().getId());

        return response;
    }

}
