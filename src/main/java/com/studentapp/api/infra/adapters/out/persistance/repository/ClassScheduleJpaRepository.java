package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.ClassScheduleEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassScheduleJpaRepository extends JpaRepository<ClassScheduleEntity, UUID> {

    Page<ClassScheduleEntity> findBySubject(SubjectEntity subject, Pageable pageable);
}
