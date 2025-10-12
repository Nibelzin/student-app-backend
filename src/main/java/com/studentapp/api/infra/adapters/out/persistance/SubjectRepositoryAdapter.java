package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.SubjectMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.PeriodJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.SubjectJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubjectRepositoryAdapter implements SubjectRepositoryPort {

    private final SubjectJpaRepository subjectJpaRepository;
    private final PeriodJpaRepository periodJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final SubjectMapper subjectMapper;


    @Override
    public Subject save(Subject subject) {
        SubjectEntity subjectEntity = subjectMapper.toEntity(subject);

        SubjectEntity persistedSubjectEntity = subjectJpaRepository.save(subjectEntity);

        return subjectMapper.toDomain(persistedSubjectEntity);
    }

    @Override
    public Subject update(Subject subject) {
        SubjectEntity subjectEntity = subjectMapper.toEntity(subject);

        SubjectEntity persistedSubjectEntity = subjectJpaRepository.save(subjectEntity);

        return subjectMapper.toDomain(persistedSubjectEntity);
    }

    @Override
    public Optional<Subject> findById(UUID id) {
        Optional<SubjectEntity> optionalSubjectEntity = subjectJpaRepository.findById(id);

        return optionalSubjectEntity.map(subjectMapper::toDomain);
    }

    @Override
    public Page<Subject> findAll(Pageable pageable) {
        Page<SubjectEntity> subjectEntityPage = subjectJpaRepository.findAll(pageable);

        return subjectEntityPage.map(subjectMapper::toDomain);
    }

    @Override
    public Page<Subject> findByPeriodId(UUID periodId, Pageable pageable){

        PeriodEntity periodEntity = periodJpaRepository.findById(periodId).orElseThrow(
                () -> new ResourceNotFoundException("Período não encontrado.")
        );

        Page<SubjectEntity> subjectEntityPage = subjectJpaRepository.findByPeriod(periodEntity, pageable);

        return subjectEntityPage.map(subjectMapper::toDomain);
    }

    @Override
    public Page<Subject> findByUserId(UUID userId, Pageable pageable){

        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Page<SubjectEntity> subjectEntityPage = subjectJpaRepository.findByUser(userEntity, pageable);

        return subjectEntityPage.map(subjectMapper::toDomain);
    }

    @Override
    public void delete(UUID id){
        subjectJpaRepository.deleteById(id);
    }


}
