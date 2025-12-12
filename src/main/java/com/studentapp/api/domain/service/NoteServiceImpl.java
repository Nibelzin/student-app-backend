package com.studentapp.api.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.NoteUseCase;
import com.studentapp.api.domain.port.out.NoteRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteUseCase {

    private final NoteRepositoryPort noteRepository;
    private final UserRepositoryPort userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Note createNote(String content, Boolean isPinned, UUID userId) {

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

        if (noteUpdateData.content() != null && !noteUpdateData.content().isBlank()) {
            existingNote.setContent(noteUpdateData.content());
        }

        if (noteUpdateData.isPinned() != null) {
            existingNote.setPinned(noteUpdateData.isPinned());
        }

        return noteRepository.update(existingNote);
    }

    @Override
    public Optional<Note> findNoteById(UUID id) {
        return noteRepository.findById(id);
    }

    @Override
    public Page<Note> findPinnedNotesByUserId(UUID userId, Pageable pageable) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }

        return noteRepository.findPinnedByUserId(userId, pageable);
    }

    @Override
    public Page<Note> findAllNotes(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public Page<Note> findNotesByUserId(UUID userId, String searchTerm, Pageable pageable) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }


        if (searchTerm == null || searchTerm.isBlank() || searchTerm.equals("@")) {

            return noteRepository.findByUserId(userId, pageable);
        }

        final String normalizedSearchTerm = removeAccents(searchTerm).toLowerCase();

        List<Note> allNotes = noteRepository.findAllByUserId(userId);

        List<Note> filteredNotes = allNotes.stream()
                .filter(note -> {
                    try {
                        JsonNode rootNode = objectMapper.readTree(note.getContent());
                        return  nodeContainsText(rootNode, normalizedSearchTerm);
                    } catch (Exception e){
                        return false;
                    }
                })
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredNotes.size());

        if (start > filteredNotes.size()) {
            return new PageImpl<>(List.of(), pageable, filteredNotes.size());
        }

        List<Note> pageContent = filteredNotes.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filteredNotes.size());
    }
    @Override
    public void deleteNote(UUID id) {
        noteRepository.delete(id);
    }

    private boolean nodeContainsText(JsonNode node, String normalizedSearchTerm) {
        if( normalizedSearchTerm == null || normalizedSearchTerm.isEmpty() || normalizedSearchTerm.equals("@")) {
            return true;
        }

        if (node.has("type")) {
            String type = node.get("type").asText();

            if ("text".equals(type) && node.has("text")) {
                String text = node.get("text").asText();
                if (text != null) {
                    String normalizedText = removeAccents(text).toLowerCase();
                    return normalizedText.contains(normalizedSearchTerm);
                }
            }

            if ("mention".equals(type) && node.has("attrs")) {
                JsonNode attrs = node.get("attrs");
                if (attrs.has("label")) {
                    String label = attrs.get("label").asText();
                    if(label != null){
                        String normalizedLabel = removeAccents(label).toLowerCase();
                        return normalizedLabel.contains(normalizedSearchTerm);
                    }
                }
            }
        }

        if (node.has("content") && node.get("content").isArray()) {
            for (JsonNode childNode : node.get("content")) {
                if (nodeContainsText(childNode, normalizedSearchTerm)) {
                    return true;
                }
            }
        }

        return false;
    }

    private String removeAccents(String value){
        if (value == null){
            return "";
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

}
