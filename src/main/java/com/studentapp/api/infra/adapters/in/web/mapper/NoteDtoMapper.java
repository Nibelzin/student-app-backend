package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class NoteDtoMapper {

    public Note toDomain(NoteCreateRequest request, User user) {
        return Note.create(request.getContent(), request.getIsPinned(), user);
    }

    @Mapping(target = "isPinned", source = "pinned")
    @Mapping(target = "userId", expression = "java(note.getUser().getId())")
    public abstract NoteResponse toResponse(Note note);
}
