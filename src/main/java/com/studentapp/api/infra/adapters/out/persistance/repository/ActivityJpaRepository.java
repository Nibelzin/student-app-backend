package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, UUID>, JpaSpecificationExecutor<ActivityEntity> {

    @Query("SELECT a FROM ActivityEntity a WHERE a.isCompleted = false AND a.dueDate BETWEEN :from AND :to")
    List<ActivityEntity> findIncompleteAndDueBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
