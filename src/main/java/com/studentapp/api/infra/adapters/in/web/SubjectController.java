package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.MaterialUseCase;
import com.studentapp.api.domain.port.in.SubjectUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.material.MaterialResponse;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectResponse;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.MaterialDtoMapper;
import com.studentapp.api.infra.adapters.in.web.mapper.SubjectDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
@Tag(name = "subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectUseCase subjectUseCase;
    private final MaterialUseCase materialUseCase;
    private final SubjectDtoMapper subjectDtoMapper;
    private final MaterialDtoMapper materialDtoMapper;

    @GetMapping
    public ResponseEntity<Page<SubjectResponse>> getAllSubjects(Pageable pageable){
        Page<Subject> subjectPage = subjectUseCase.findAllSubjects(pageable);

        Page<SubjectResponse> subjectResponsePage = subjectPage.map(subjectDtoMapper::toResponse);

        return ResponseEntity.ok(subjectResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable UUID id){

        Optional<Subject> foundSubject = subjectUseCase.findSubjectById(id);

        return foundSubject
                .map(subjectDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/materials")
    public ResponseEntity<Page<MaterialResponse>> getSubjectMaterialsById(@PathVariable UUID id, Pageable pageable){
        Page<Material> subjectMaterialsPage = materialUseCase.findMaterialsBySubjectId(id, pageable);

        Page<MaterialResponse> subjectMaterialsPageResponse = subjectMaterialsPage.map(materialDtoMapper::toResponse);

        return ResponseEntity.ok(subjectMaterialsPageResponse);
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(@Valid @RequestBody SubjectCreateRequest subjectCreateRequest) {

        Subject createdSubject = subjectUseCase.createSubject(subjectCreateRequest.getName(), subjectCreateRequest.getProfessor(), subjectCreateRequest.getClassroom(), subjectCreateRequest.getColor(), subjectCreateRequest.getMaxAbsencesAllowed(), subjectCreateRequest.getPeriodId(), subjectCreateRequest.getUserId());

        SubjectResponse subjectResponse = subjectDtoMapper.toResponse(createdSubject);

        return ResponseEntity.ok(subjectResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(@PathVariable UUID id, @Valid @RequestBody SubjectUpdateRequest subjectUpdateRequest) {
        SubjectUseCase.SubjectUpdateData updateData = new SubjectUseCase.SubjectUpdateData(
                subjectUpdateRequest.getName(),
                subjectUpdateRequest.getProfessor(),
                subjectUpdateRequest.getClassroom(),
                subjectUpdateRequest.getColor(),
                subjectUpdateRequest.getMaxAbsencesAllowed(),
                subjectUpdateRequest.getPeriodId()
        );

        Subject updatedSubject = subjectUseCase.updateSubject(id, updateData);

        SubjectResponse subjectResponse = subjectDtoMapper.toResponse(updatedSubject);

        return ResponseEntity.ok(subjectResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectResponse> deleteSubject(@PathVariable UUID id){
        subjectUseCase.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

}
