package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.AssessmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AssessmentJpaRepository extends JpaRepository<AssessmentEntity, UUID>, JpaSpecificationExecutor<AssessmentEntity> {
}
