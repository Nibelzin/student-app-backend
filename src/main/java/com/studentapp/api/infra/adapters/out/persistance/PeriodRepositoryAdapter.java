package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.out.PeriodRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.PeriodMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.PeriodJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PeriodRepositoryAdapter implements PeriodRepositoryPort {

    private final PeriodJpaRepository periodJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PeriodMapper periodMapper;

    @Override
    public Period save(Period period) {
        PeriodEntity periodEntity = periodMapper.toEntity(period);

        PeriodEntity persistedPeriodEntity = periodJpaRepository.save(periodEntity);

        return periodMapper.toDomain(persistedPeriodEntity);
    }

    @Override
    public Period update(Period period) {
        PeriodEntity periodEntity = periodMapper.toEntity(period);

        PeriodEntity persistedPeriodEntity = periodJpaRepository.save(periodEntity);

        return periodMapper.toDomain(persistedPeriodEntity);
    }

    @Override
    public void delete(UUID id) {
        periodJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Period> findById(UUID id) {
        Optional<PeriodEntity> optionalPeriodEntity = periodJpaRepository.findById(id);

        return optionalPeriodEntity.map(periodMapper::toDomain);
    }

    @Override
    public Page<Period> findAll(Pageable pageable) {
        Page<PeriodEntity> periodEntityPage = periodJpaRepository.findAll(pageable);

        return periodEntityPage.map(periodMapper::toDomain);
    }

    @Override
    public List<Period> findByUserId(UUID userId) {

        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        List<PeriodEntity> userPeriods = periodJpaRepository.findByUser(user);

        return userPeriods.stream().map(periodMapper::toDomain).toList();
    }

    @Override
    public Optional<Period> findCurrentByUserId(UUID userId) {
        return periodJpaRepository.findByUser_IdAndIsCurrentTrue(userId)
                .map(periodMapper::toDomain);
    }

}
