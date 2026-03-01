package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.ClassSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface ClassScheduleUseCase {

    record CreateClassScheduleData(Integer weekDay, LocalTime startTime,
            LocalTime endTime, String location, UUID subjectId) {}

    record UpdateClassScheduleData(Integer weekDay, LocalTime startTime,
            LocalTime endTime, String location) {}

    ClassSchedule createClassSchedule(CreateClassScheduleData data);
    ClassSchedule updateClassSchedule(UUID id, UpdateClassScheduleData data);
    Optional<ClassSchedule> findClassScheduleById(UUID id);
    Page<ClassSchedule> findBySubjectId(UUID subjectId, Pageable pageable);
    void deleteClassSchedule(UUID id);
}
