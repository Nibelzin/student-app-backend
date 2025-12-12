package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepositoryPort {
    Note save(Note note);
    Note update(Note note);
    void delete(UUID id);
    Optional<Note> findById(UUID id);
    Page<Note> findPinnedByUserId(UUID userId, Pageable pageable);
    Page<Note> findAll(Pageable pageable);
    Page<Note> findByUserId(UUID userId, Pageable pageable);

    List<Note> findAllByUserId(UUID userId);
}
