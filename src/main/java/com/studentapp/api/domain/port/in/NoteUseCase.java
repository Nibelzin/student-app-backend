package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NoteUseCase {

    record NoteUpdateData(String content, Boolean isPinned){};

    Note createNote(String content, Boolean isPinned, UUID userId);

    Note updateNote(UUID id, NoteUpdateData noteUpdateDate);

    Optional<Note> findNoteById(UUID id);

    Page<Note> findPinnedNotesByUserId(UUID userId, Pageable pageable);

    Page<Note> findAllNotes(Pageable pageable);

    Page<Note> findNotesByUserId(UUID userId, Pageable pageable);

    void deleteNote(UUID id);

}
