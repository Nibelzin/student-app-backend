package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.NoteUseCase;
import com.studentapp.api.domain.port.out.NoteRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteUseCase {

    private final NoteRepositoryPort noteRepository;
    private final UserRepositoryPort userRepository;

    @Override
    public Note createNote(String content, Boolean isPinned, UUID userId){

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Note newNote = Note.create(content, isPinned, user);

        return noteRepository.save(newNote);
    }

    @Override
    public Note updateNote(UUID id, NoteUpdateData noteUpdateData) {

        Note existingNote = noteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Nota não encontrada.")
        );

        if(noteUpdateData.content() != null && !noteUpdateData.content().isBlank()){
            existingNote.setContent(noteUpdateData.content());
        }

        if(noteUpdateData.isPinned() != null){
            existingNote.setPinned(noteUpdateData.isPinned());
        }

        return noteRepository.update(existingNote);
    }

    @Override
    public Optional<Note> findNoteById(UUID id){
        return noteRepository.findById(id);
    }

    @Override
    public Page<Note> findPinnedNotesByUserId(UUID userId, Pageable pageable){

        if (userRepository.findById(userId).isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }

        return noteRepository.findPinnedByUserId(userId, pageable);
    }

    @Override
    public Page<Note> findAllNotes(Pageable pageable){
        return noteRepository.findAll(pageable);
    }

    @Override
    public Page<Note> findNotesByUserId(UUID userId, Pageable pageable){

        if (userRepository.findById(userId).isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }

        return noteRepository.findByUserId(userId, pageable);
    }

    @Override
    public void deleteNote(UUID id){
        noteRepository.delete(id);
    }

}
