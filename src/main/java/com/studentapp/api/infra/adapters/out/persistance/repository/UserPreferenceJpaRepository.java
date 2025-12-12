package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserPreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceJpaRepository extends JpaRepository<UserPreferenceEntity, UUID> {

    Optional<UserPreferenceEntity> findByUser(UserEntity user);

}
