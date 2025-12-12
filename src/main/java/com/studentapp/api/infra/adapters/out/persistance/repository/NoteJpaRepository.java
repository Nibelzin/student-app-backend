package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.NoteEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteJpaRepository extends JpaRepository<NoteEntity, UUID> {

    Page<NoteEntity> findByUser(UserEntity user, Pageable pageable);

    List<NoteEntity> findByUser(UserEntity user);

    Page<NoteEntity> findByUser_IdAndIsPinnedTrue(UUID userId, Pageable pageable);

}
