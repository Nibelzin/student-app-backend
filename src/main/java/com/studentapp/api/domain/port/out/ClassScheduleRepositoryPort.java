package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.ClassSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ClassScheduleRepositoryPort {
    ClassSchedule save(ClassSchedule classSchedule);
    Optional<ClassSchedule> findById(UUID id);
    Page<ClassSchedule> findBySubjectId(UUID subjectId, Pageable pageable);
    void delete(UUID id);
}
