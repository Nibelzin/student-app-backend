package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.PlannerEventEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlannerEventJpaRepository extends JpaRepository<PlannerEventEntity, UUID> {
    public Page<PlannerEventEntity> findByUser(UserEntity user, Pageable pageable);
}
