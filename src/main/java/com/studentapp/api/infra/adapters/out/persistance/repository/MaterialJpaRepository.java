package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.MaterialEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaterialJpaRepository extends JpaRepository<MaterialEntity, UUID> {

    Page<MaterialEntity> findBySubject(SubjectEntity subject, Pageable pageable);


}
