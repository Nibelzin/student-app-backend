package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Assessment;
import com.studentapp.api.domain.port.in.AssessmentUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.assessment.AssessmentCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.assessment.AssessmentResponse;
import com.studentapp.api.infra.adapters.in.web.dto.assessment.AssessmentUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.AssessmentDtoMapper;
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
@RequestMapping(value = "/assessments", produces = "application/json")
@Tag(name = "assessment")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentUseCase assessmentUseCase;
    private final AssessmentDtoMapper assessmentDtoMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> getAssessmentById(@PathVariable UUID id) {
        return assessmentUseCase.findAssessmentById(id)
                .map(assessmentDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<Page<AssessmentResponse>> getAssessmentsBySubjectId(
            @PathVariable UUID subjectId, Pageable pageable) {
        Page<Assessment> page = assessmentUseCase.findBySubjectId(subjectId, pageable);
        return ResponseEntity.ok(page.map(assessmentDtoMapper::toResponse));
    }

    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(
            @Valid @RequestBody AssessmentCreateRequest request) {
        AssessmentUseCase.CreateAssessmentData data = new AssessmentUseCase.CreateAssessmentData(
                request.getTitle(),
                request.getAssessmentDate(),
                request.getGrade(),
                request.getMaxGrade(),
                request.getWeight(),
                request.getSubjectId()
        );
        Assessment created = assessmentUseCase.createAssessment(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentDtoMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentResponse> updateAssessment(
            @PathVariable UUID id, @Valid @RequestBody AssessmentUpdateRequest request) {
        AssessmentUseCase.UpdateAssessmentData data = new AssessmentUseCase.UpdateAssessmentData(
                request.getTitle(),
                request.getAssessmentDate(),
                request.getGrade(),
                request.getMaxGrade(),
                request.getWeight()
        );
        Assessment updated = assessmentUseCase.updateAssessment(id, data);
        return ResponseEntity.ok(assessmentDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable UUID id) {
        assessmentUseCase.deleteAssessment(id);
        return ResponseEntity.noContent().build();
    }
}
