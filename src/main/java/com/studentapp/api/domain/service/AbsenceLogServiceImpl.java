package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.AbsenceLog;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.AbsenceLogUseCase;
import com.studentapp.api.domain.port.out.AbsenceLogRepositoryPort;
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
public class AbsenceLogServiceImpl implements AbsenceLogUseCase {

    private final AbsenceLogRepositoryPort absenceLogRepositoryPort;
    private final SubjectRepositoryPort subjectRepositoryPort;

    @Override
    public AbsenceLog createAbsenceLog(CreateAbsenceLogData createAbsenceLogData) {
        Subject subject = subjectRepositoryPort.findById(createAbsenceLogData.subjectId()).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        AbsenceLog newAbsenceLog = AbsenceLog.create(createAbsenceLogData.absenceDate(), createAbsenceLogData.notes(), subject);

        return absenceLogRepositoryPort.save(newAbsenceLog);
    }

    @Override
    public AbsenceLog updateAbsenceLog(UUID id, UpdateAbsenceLogData updateAbsenceLogData) {
        AbsenceLog existingAbsenceLog = absenceLogRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Registro de ausência não encontrado.")
        );

        if(updateAbsenceLogData.absenceDate() != null){
            existingAbsenceLog.setAbsenceDate(updateAbsenceLogData.absenceDate());
        }

        if(updateAbsenceLogData.notes() != null && !updateAbsenceLogData.notes().isBlank()){
            existingAbsenceLog.setNotes(updateAbsenceLogData.notes());
        }

        return absenceLogRepositoryPort.save(existingAbsenceLog);
    }

    @Override
    public Optional<AbsenceLog> findAbsenceLogById(UUID id){
        return absenceLogRepositoryPort.findById(id);
    }

    @Override
    public Page<AbsenceLog> findAllAbsenceLogs(Pageable pageable){
        return absenceLogRepositoryPort.findAll(pageable);
    }

    @Override
    public Page<AbsenceLog> findAbsenceLogsBySubjectId(UUID subjectId, Pageable pageable){
        return absenceLogRepositoryPort.findBySubjectId(subjectId, pageable);
    }

    @Override
    public void deleteAbsenceLog(UUID id){
        absenceLogRepositoryPort.delete(id);
    }

}
