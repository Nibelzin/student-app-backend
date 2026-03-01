package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.AbsenceLog;
import com.studentapp.api.domain.port.in.AbsenceLogUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.absenceLog.AbsenceLogCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.absenceLog.AbsenceLogResponse;
import com.studentapp.api.infra.adapters.in.web.dto.absenceLog.AbsenceLogUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.AbsenceLogDtoMapper;
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
@RequestMapping(value = "/absence-logs", produces = "application/json")
@Tag(name = "absence-log")
@RequiredArgsConstructor
public class AbsenceLogController {

    private final AbsenceLogUseCase absenceLogUseCase;
    private final AbsenceLogDtoMapper absenceLogDtoMapper;

    @GetMapping
    public ResponseEntity<Page<AbsenceLogResponse>> getAllAbsenceLogs(Pageable pageable) {
        Page<AbsenceLog> page = absenceLogUseCase.findAllAbsenceLogs(pageable);
        return ResponseEntity.ok(page.map(absenceLogDtoMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceLogResponse> getAbsenceLogById(@PathVariable UUID id) {
        return absenceLogUseCase.findAbsenceLogById(id)
                .map(absenceLogDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<Page<AbsenceLogResponse>> getAbsenceLogsBySubjectId(@PathVariable UUID subjectId, Pageable pageable) {
        Page<AbsenceLog> page = absenceLogUseCase.findAbsenceLogsBySubjectId(subjectId, pageable);
        return ResponseEntity.ok(page.map(absenceLogDtoMapper::toResponse));
    }

    @PostMapping
    public ResponseEntity<AbsenceLogResponse> createAbsenceLog(@Valid @RequestBody AbsenceLogCreateRequest request) {
        AbsenceLogUseCase.CreateAbsenceLogData data = new AbsenceLogUseCase.CreateAbsenceLogData(
                request.getAbsenceDate(),
                request.getNotes(),
                request.getSubjectId()
        );
        AbsenceLog created = absenceLogUseCase.createAbsenceLog(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(absenceLogDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceLogResponse> updateAbsenceLog(@PathVariable UUID id, @Valid @RequestBody AbsenceLogUpdateRequest request) {
        AbsenceLogUseCase.UpdateAbsenceLogData data = new AbsenceLogUseCase.UpdateAbsenceLogData(
                request.getAbsenceDate(),
                request.getNotes()
        );
        AbsenceLog updated = absenceLogUseCase.updateAbsenceLog(id, data);
        return ResponseEntity.ok(absenceLogDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsenceLog(@PathVariable UUID id) {
        absenceLogUseCase.deleteAbsenceLog(id);
        return ResponseEntity.noContent().build();
    }

}