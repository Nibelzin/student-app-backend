package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.PeriodEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectJpaRepository extends JpaRepository<SubjectEntity, UUID> {

    Page<SubjectEntity> findByPeriod(PeriodEntity period, Pageable pageable);

    Page<SubjectEntity> findByUser(UserEntity user, Pageable pageable);

}
