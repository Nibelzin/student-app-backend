package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.AbsenceLogEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AbsenceLogJpaRepository extends JpaRepository<AbsenceLogEntity, UUID> {

    Page<AbsenceLogEntity> findBySubject(SubjectEntity subject, Pageable pageable);

    long countBySubject(SubjectEntity subject);

}
