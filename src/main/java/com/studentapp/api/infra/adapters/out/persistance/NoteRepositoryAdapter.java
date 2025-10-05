package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.port.out.NoteRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.NoteEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.NoteMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.NoteJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NoteRepositoryAdapter implements NoteRepositoryPort {

    private final NoteJpaRepository noteJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final NoteMapper noteMapper;

    @Override
    public Note save(Note note){
        NoteEntity noteEntity = noteMapper.toEntity(note);

        NoteEntity persistedNoteEntity = noteJpaRepository.save(noteEntity);

        return noteMapper.toDomain(persistedNoteEntity);
    }

    @Override
    public Note update(Note note){
        NoteEntity noteEntity = noteMapper.toEntity(note);

        NoteEntity persistedNoteEntity = noteJpaRepository.save(noteEntity);

        return noteMapper.toDomain(persistedNoteEntity);
    }

    @Override
    public void delete(UUID id) { noteJpaRepository.deleteById(id); }

    @Override
    public Optional<Note> findById(UUID id) {
        Optional<NoteEntity> optionalNoteEntity = noteJpaRepository.findById(id);

        return optionalNoteEntity.map(noteMapper::toDomain);
    }

    @Override
    public Page<Note> findPinnedByUserId(UUID id, Pageable pageable){
        return noteJpaRepository.findByUser_IdAndIsPinnedTrue(id, pageable)
                .map(noteMapper::toDomain);
    }

    @Override
    public Page<Note> findAll(Pageable pageable){
        Page<NoteEntity> noteEntityPage = noteJpaRepository.findAll(pageable);
        return noteEntityPage.map(noteMapper::toDomain);
    }

    @Override
    public Page<Note> findByUserId(UUID id, Pageable pageable){

        UserEntity user = userJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        Page<NoteEntity> noteEntityPage = noteJpaRepository.findByUser(user, pageable);

        return noteEntityPage.map(noteMapper::toDomain);
    }

}
