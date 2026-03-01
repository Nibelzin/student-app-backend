package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.ClassSchedule;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.ClassScheduleUseCase;
import com.studentapp.api.domain.port.out.ClassScheduleRepositoryPort;
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
public class ClassScheduleServiceImpl implements ClassScheduleUseCase {

    private final ClassScheduleRepositoryPort classScheduleRepositoryPort;
    private final SubjectRepositoryPort subjectRepositoryPort;

    @Override
    public ClassSchedule createClassSchedule(CreateClassScheduleData data) {
        Subject subject = subjectRepositoryPort.findById(data.subjectId()).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        ClassSchedule newClassSchedule = ClassSchedule.create(
                data.weekDay(), data.startTime(), data.endTime(), data.location(), subject
        );

        return classScheduleRepositoryPort.save(newClassSchedule);
    }

    @Override
    public ClassSchedule updateClassSchedule(UUID id, UpdateClassScheduleData data) {
        ClassSchedule existing = classScheduleRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Horário de aula não encontrado.")
        );

        if (data.weekDay() != null) {
            existing.setWeekDay(data.weekDay());
        }

        if (data.startTime() != null) {
            existing.setStartTime(data.startTime());
        }

        if (data.endTime() != null) {
            existing.setEndTime(data.endTime());
        }

        if (data.location() != null && !data.location().isBlank()) {
            existing.setLocation(data.location());
        }

        return classScheduleRepositoryPort.save(existing);
    }

    @Override
    public Optional<ClassSchedule> findClassScheduleById(UUID id) {
        return classScheduleRepositoryPort.findById(id);
    }

    @Override
    public Page<ClassSchedule> findBySubjectId(UUID subjectId, Pageable pageable) {
        return classScheduleRepositoryPort.findBySubjectId(subjectId, pageable);
    }

    @Override
    public void deleteClassSchedule(UUID id) {
        classScheduleRepositoryPort.delete(id);
    }
}
