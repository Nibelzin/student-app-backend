package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.domain.port.out.PlannerEventRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.PlannerEventEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.PlannerEventMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.PlannerEventJpaRepository;
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
public class PlannerEventRepositoryAdapter implements PlannerEventRepositoryPort {

    private final PlannerEventJpaRepository plannerEventJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final SubjectRepositoryAdapter subjectRepositoryAdapter;
    private final PlannerEventMapper plannerEventMapper;

    @Override
    public PlannerEvent save(PlannerEvent plannerEvent){
        PlannerEventEntity plannerEventEntity = plannerEventMapper.toEntity(plannerEvent);

        PlannerEventEntity savedPlannerEventEntity = plannerEventJpaRepository.save(plannerEventEntity);

        return plannerEventMapper.toDomain(savedPlannerEventEntity);
    }

    @Override
    public PlannerEvent update(PlannerEvent plannerEvent){
        PlannerEventEntity plannerEventEntity = plannerEventMapper.toEntity(plannerEvent);

        PlannerEventEntity savedPlannerEventEntity = plannerEventJpaRepository.save(plannerEventEntity);

        return plannerEventMapper.toDomain(savedPlannerEventEntity);
    }

    @Override
    public Optional<PlannerEvent> findById(UUID id){
        Optional<PlannerEventEntity> optionalPlannerEventEntity = plannerEventJpaRepository.findById(id);

        return optionalPlannerEventEntity.map(plannerEventMapper::toDomain);
    }

    @Override
    public Page<PlannerEvent> findByUserId(UUID userId, Pageable pageable){
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Page<PlannerEventEntity> plannerEventEntityPage = plannerEventJpaRepository.findByUser(userEntity, pageable);

        return plannerEventEntityPage.map(plannerEventMapper::toDomain);
    }

    @Override
    public void delete(UUID id){
        plannerEventJpaRepository.deleteById(id);
    }

}
