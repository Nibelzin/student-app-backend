package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SubjectUseCase {

    record SubjectUpdateData(String name, String professor, String classroom, String color, Integer maxAbsencesAllowed, UUID periodId){};

    Subject createSubject(String name, String professor, String classroom, String color, Integer maxAbsencesAllowed, UUID periodId, UUID userId);

    Subject updateSubject(UUID id, SubjectUpdateData subjectUpdateData);

    Optional<Subject> findSubjectById(UUID id);

    Page<Subject> findAllSubjects(Pageable pageable);

    Page<Subject> findSubjectsByPeriodId(UUID periodId, Pageable pageable);

    Page<Subject> findSubjectsByUserId(UUID userId, Pageable pageable);

    void deleteSubject(UUID id);

}
