package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.ClassSchedule;
import com.studentapp.api.domain.port.in.ClassScheduleUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.classSchedule.ClassScheduleCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.classSchedule.ClassScheduleResponse;
import com.studentapp.api.infra.adapters.in.web.dto.classSchedule.ClassScheduleUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.ClassScheduleDtoMapper;
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
@RequestMapping(value = "/class-schedules", produces = "application/json")
@Tag(name = "class-schedule")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final ClassScheduleUseCase classScheduleUseCase;
    private final ClassScheduleDtoMapper classScheduleDtoMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ClassScheduleResponse> getClassScheduleById(@PathVariable UUID id) {
        return classScheduleUseCase.findClassScheduleById(id)
                .map(classScheduleDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<Page<ClassScheduleResponse>> getClassSchedulesBySubjectId(
            @PathVariable UUID subjectId, Pageable pageable) {
        Page<ClassSchedule> page = classScheduleUseCase.findBySubjectId(subjectId, pageable);
        return ResponseEntity.ok(page.map(classScheduleDtoMapper::toResponse));
    }

    @PostMapping
    public ResponseEntity<ClassScheduleResponse> createClassSchedule(
            @Valid @RequestBody ClassScheduleCreateRequest request) {
        ClassScheduleUseCase.CreateClassScheduleData data = new ClassScheduleUseCase.CreateClassScheduleData(
                request.getWeekDay(),
                request.getStartTime(),
                request.getEndTime(),
                request.getLocation(),
                request.getSubjectId()
        );
        ClassSchedule created = classScheduleUseCase.createClassSchedule(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(classScheduleDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassScheduleResponse> updateClassSchedule(
            @PathVariable UUID id, @Valid @RequestBody ClassScheduleUpdateRequest request) {
        ClassScheduleUseCase.UpdateClassScheduleData data = new ClassScheduleUseCase.UpdateClassScheduleData(
                request.getWeekDay(),
                request.getStartTime(),
                request.getEndTime(),
                request.getLocation()
        );
        ClassSchedule updated = classScheduleUseCase.updateClassSchedule(id, data);
        return ResponseEntity.ok(classScheduleDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSchedule(@PathVariable UUID id) {
        classScheduleUseCase.deleteClassSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
