package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Assessment;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.AssessmentUseCase;
import com.studentapp.api.domain.port.out.AssessmentRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentUseCase {

    private final AssessmentRepositoryPort assessmentRepositoryPort;
    private final SubjectRepositoryPort subjectRepositoryPort;

    @Override
    public Assessment createAssessment(CreateAssessmentData data) {
        Subject subject = subjectRepositoryPort.findById(data.subjectId()).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        Assessment newAssessment = Assessment.create(
                data.title(), data.assessmentDate(), data.grade(),
                data.maxGrade(), data.weight(), subject
        );

        return assessmentRepositoryPort.save(newAssessment);
    }

    @Override
    public Assessment updateAssessment(UUID id, UpdateAssessmentData data) {
        Assessment existing = assessmentRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Avaliação não encontrada.")
        );

        if (data.title() != null && !data.title().isBlank()) {
            existing.setTitle(data.title());
        }

        if (data.assessmentDate() != null) {
            existing.setAssessmentDate(data.assessmentDate());
        }

        if (data.grade() != null) {
            existing.setGrade(data.grade());
        }

        if (data.maxGrade() != null) {
            existing.setMaxGrade(data.maxGrade());
        }

        if (data.weight() != null) {
            existing.setWeight(data.weight());
        }

        return assessmentRepositoryPort.save(existing);
    }

    @Override
    public Optional<Assessment> findAssessmentById(UUID id) {
        return assessmentRepositoryPort.findById(id);
    }

    @Override
    public Page<Assessment> findBySubjectId(UUID subjectId, Pageable pageable) {
        return assessmentRepositoryPort.findBySubjectId(subjectId, pageable);
    }

    @Override
    public void deleteAssessment(UUID id) {
        assessmentRepositoryPort.delete(id);
    }
}
