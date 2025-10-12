package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.SubjectUseCase;
import com.studentapp.api.domain.port.out.PeriodRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.InvalidPeriodException;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectUseCase {

    private final SubjectRepositoryPort subjectRepository;
    private final PeriodRepositoryPort periodRepository;
    private final UserRepositoryPort userRepository;

    @Override
    public Subject createSubject(String name, String professor, String classroom, String color, UUID periodId, UUID userId){

        if (periodId == null) {
            throw new IllegalArgumentException("O ID do período é obrigatório para criar uma matéria");
        }


        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Period period = periodRepository.findById(periodId).orElseThrow(
                () -> new ResourceNotFoundException("Periodo não encontrado.")
        );

        if(user.getId() != period.getUser().getId()){
            throw new InvalidPeriodException("Periodo fornecido não pertence ao usuário informado.");
        }

        Subject newSubject = Subject.create(name, professor, classroom, color, user, period);

        return subjectRepository.save(newSubject);
    }

    @Override
    public Subject updateSubject(UUID id, SubjectUpdateData subjectUpdateData) {

        Subject existingSubject = subjectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        if (subjectUpdateData.name() != null && !subjectUpdateData.name().isBlank()) {
            existingSubject.setName(subjectUpdateData.name());
        }

        if (subjectUpdateData.professor() != null && !subjectUpdateData.professor().isBlank()) {
            existingSubject.setProfessor(subjectUpdateData.professor());
        }

        if (subjectUpdateData.classroom() != null && !subjectUpdateData.classroom().isBlank()) {
            existingSubject.setClassroom(subjectUpdateData.classroom());
        }

        if (subjectUpdateData.color() != null && !subjectUpdateData.color().isBlank()) {
            existingSubject.setColor(subjectUpdateData.color());
        }

        if (subjectUpdateData.periodId() != null) {

            Period period = periodRepository.findById(subjectUpdateData.periodId()).orElseThrow(
                    () -> new ResourceNotFoundException("Período não encontrado.")
            );

            existingSubject.setPeriod(period);
        }

        return subjectRepository.update(existingSubject);
    }

    @Override
    public Optional<Subject> findSubjectById(UUID id){
        return Optional.ofNullable(subjectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Período não encontrado.")
        ));
    }

    @Override
    public Page<Subject> findAllSubjects(Pageable pageable){
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Page<Subject> findSubjectsByPeriodId(UUID periodId, Pageable pageable){
        return subjectRepository.findByPeriodId(periodId, pageable);
    }

    @Override
    public Page<Subject> findSubjectsByUserId(UUID userId, Pageable pageable){
        return subjectRepository.findByUserId(userId, pageable);
    }

    @Override
    public void deleteSubject(UUID id){
        subjectRepository.delete(id);
    }

}
