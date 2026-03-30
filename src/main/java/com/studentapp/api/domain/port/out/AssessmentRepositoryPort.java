package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.assessment.Assessment;
import com.studentapp.api.domain.port.in.AssessmentUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AssessmentRepositoryPort {
    Assessment save(Assessment assessment);
    Optional<Assessment> findById(UUID id);
    Page<Assessment> findByQuery(AssessmentUseCase.AssessmentQueryData queryData, Pageable pageable);
    void delete(UUID id);
}
