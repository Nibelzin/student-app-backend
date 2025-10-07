package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.port.in.NoteUseCase;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteResponse;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.NoteDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value ="/notes", produces = "application/json")
@Tag(name = "note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteUseCase noteUseCase;
    private final NoteDtoMapper noteDtoMapper;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteCreateRequest noteCreateRequest) {

        Note createdNote = noteUseCase.createNote(noteCreateRequest.getContent(), noteCreateRequest.getIsPinned(), noteCreateRequest.getUserId());

        NoteResponse noteResponse = noteDtoMapper.toResponse(createdNote);

        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable UUID id, @Valid @RequestBody NoteUpdateRequest noteUpdateRequest) {

        NoteUseCase.NoteUpdateData updateData = new NoteUseCase.NoteUpdateData(
                noteUpdateRequest.getContent(),
                noteUpdateRequest.getIsPinned()
        );

        Note updatedNote = noteUseCase.updateNote(id, updateData);

        NoteResponse noteResponse = noteDtoMapper.toResponse(updatedNote);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable UUID id){

        Optional<Note> foundNote = noteUseCase.findNoteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(noteDtoMapper.toResponse(foundNote.get()));
    }

    @GetMapping
    public ResponseEntity<Page<NoteResponse>> getAllNotes(Pageable pageable){
        Page<Note> notePage = noteUseCase.findAllNotes(pageable);

        Page<NoteResponse> noteResponsePage = notePage.map(noteDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(noteResponsePage);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NoteResponse> deleteNote(@PathVariable UUID id){
        noteUseCase.deleteNote(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
