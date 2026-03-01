package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.FocusSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FocusSessionJpaRepository extends JpaRepository<FocusSessionEntity, UUID>, JpaSpecificationExecutor<FocusSessionEntity> {
}
