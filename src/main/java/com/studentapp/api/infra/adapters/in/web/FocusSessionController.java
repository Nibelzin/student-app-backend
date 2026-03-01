package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.domain.port.in.FocusSessionUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.focusSession.FocusSessionCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.focusSession.FocusSessionResponse;
import com.studentapp.api.infra.adapters.in.web.dto.focusSession.FocusSessionUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.FocusSessionDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/focus-sessions", produces = "application/json")
@Tag(name = "focus-session")
@RequiredArgsConstructor
public class FocusSessionController {

    private final FocusSessionUseCase focusSessionUseCase;
    private final FocusSessionDtoMapper focusSessionDtoMapper;

    @GetMapping
    public ResponseEntity<Page<FocusSessionResponse>> getFocusSessions(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID subjectId,
            @RequestParam(required = false) UUID activityId,
            @RequestParam(required = false) Boolean isCompleted,
            Pageable pageable) {
        FocusSessionUseCase.FocusSessionQueryData query = new FocusSessionUseCase.FocusSessionQueryData(
                userId, subjectId, activityId, isCompleted
        );
        Page<FocusSession> page = focusSessionUseCase.findByQuery(query, pageable);
        return ResponseEntity.ok(page.map(focusSessionDtoMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FocusSessionResponse> getFocusSessionById(@PathVariable UUID id) {
        return focusSessionUseCase.findFocusSessionById(id)
                .map(focusSessionDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FocusSessionResponse> createFocusSession(@Valid @RequestBody FocusSessionCreateRequest request) {
        FocusSessionUseCase.CreateFocusSessionData data = new FocusSessionUseCase.CreateFocusSessionData(
                request.getDurationSeconds(),
                Boolean.TRUE.equals(request.getIsCompleted()),
                request.getUserId(),
                request.getSubjectId(),
                request.getActivityId()
        );
        FocusSession created = focusSessionUseCase.createFocusSession(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(focusSessionDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FocusSessionResponse> updateFocusSession(
            @PathVariable UUID id,
            @RequestBody FocusSessionUpdateRequest request) {
        FocusSessionUseCase.UpdateFocusSessionData data = new FocusSessionUseCase.UpdateFocusSessionData(
                request.getDurationSeconds(),
                request.getIsCompleted()
        );
        FocusSession updated = focusSessionUseCase.updateFocusSession(id, data);
        return ResponseEntity.ok(focusSessionDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFocusSession(@PathVariable UUID id) {
        focusSessionUseCase.deleteFocusSession(id);
        return ResponseEntity.noContent().build();
    }
}
