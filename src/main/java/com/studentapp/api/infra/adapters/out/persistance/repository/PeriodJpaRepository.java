package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PeriodJpaRepository extends JpaRepository<PeriodEntity, UUID> {

    List<PeriodEntity> findByUser(UserEntity user);

}
