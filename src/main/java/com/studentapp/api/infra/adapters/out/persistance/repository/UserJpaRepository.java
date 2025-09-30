package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    public Optional<UserEntity> findByEmail(String email);

}

