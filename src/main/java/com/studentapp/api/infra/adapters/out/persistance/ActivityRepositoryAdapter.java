package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.port.in.ActivityUseCase;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import com.studentapp.api.infra.adapters.out.persistance.jpa.ActivitySpecification;
import com.studentapp.api.infra.adapters.out.persistance.mapper.ActivityMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.ActivityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityRepositoryPort {

    private final ActivityJpaRepository activityJpaRepository;
    private final ActivityMapper activityMapper;

    @Override
    public Activity save(Activity activity){
        ActivityEntity activityEntity = activityMapper.toEntity(activity);

        ActivityEntity persistedActivityEntity = activityJpaRepository.save(activityEntity);

        return activityMapper.toDomain(persistedActivityEntity);
    }

    @Override
    public Optional<Activity> findById(UUID id){
        Optional<ActivityEntity> optionalActivityEntity = activityJpaRepository.findById(id);

        return optionalActivityEntity.map(activityMapper::toDomain);
    }

    @Override
    public Page<Activity> findByQuery(ActivityUseCase.ActivityQueryData queryData, Pageable pageable){
        Specification<ActivityEntity> spec = ActivitySpecification.byCriteria(queryData);

        Page<ActivityEntity> activityEntityPage = activityJpaRepository.findAll(spec, pageable);

        return activityEntityPage.map(activityMapper::toDomain);
    }

    @Override
    public List<Activity> findIncompleteAndDueBetween(LocalDateTime from, LocalDateTime to) {
        return activityJpaRepository.findIncompleteAndDueBetween(from, to)
                .stream()
                .map(activityMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(UUID id){
        activityJpaRepository.deleteById(id);
    }

}
