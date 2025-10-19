package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.FileObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileObjectJpaRepository extends JpaRepository<FileObjectEntity, UUID> {
}
