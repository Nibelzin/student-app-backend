package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.AbsenceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AbsenceLogUseCase {

    public record CreateAbsenceLogData(LocalDate absenceDate, String notes, UUID subjectId){}
    public record UpdateAbsenceLogData(LocalDate absenceDate, String notes){}

    AbsenceLog createAbsenceLog(CreateAbsenceLogData createAbsenceLogData);
    AbsenceLog updateAbsenceLog(UUID id, UpdateAbsenceLogData updateAbsenceLogData);
    Optional<AbsenceLog> findAbsenceLogById(UUID id);
    Page<AbsenceLog> findAllAbsenceLogs(Pageable pageable);
    Page<AbsenceLog> findAbsenceLogsBySubjectId(UUID subjectId, Pageable pageable);
    void deleteAbsenceLog(UUID id);
}
