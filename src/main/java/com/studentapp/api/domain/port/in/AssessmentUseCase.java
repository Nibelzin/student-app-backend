package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AssessmentUseCase {

    record CreateAssessmentData(String title, LocalDate assessmentDate,
            Double grade, Double maxGrade, Double weight, UUID subjectId) {}

    record UpdateAssessmentData(String title, LocalDate assessmentDate,
            Double grade, Double maxGrade, Double weight) {}

    Assessment createAssessment(CreateAssessmentData data);
    Assessment updateAssessment(UUID id, UpdateAssessmentData data);
    Optional<Assessment> findAssessmentById(UUID id);
    Page<Assessment> findBySubjectId(UUID subjectId, Pageable pageable);
    void deleteAssessment(UUID id);
}
