package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.domain.port.out.UserPreferenceRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserPreferenceEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.UserPreferenceMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserPreferenceJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPreferenceRepositoryAdapter implements UserPreferenceRepositoryPort {

    private final UserPreferenceJpaRepository userPreferenceJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserPreferenceMapper userPreferenceMapper;

    @Override
    public UserPreference save (UserPreference userPreference){
        UserPreferenceEntity userPreferenceEntity = userPreferenceMapper.toEntity(userPreference);

        UserPreferenceEntity persistedUserPreferenceEntity = userPreferenceJpaRepository.save(userPreferenceEntity);

        return userPreferenceMapper.toDomain(persistedUserPreferenceEntity);
    }

    @Override
    public Optional<UserPreference> findById(UUID id){
        Optional<UserPreferenceEntity> optionalUserPreferenceEntity = userPreferenceJpaRepository.findById(id);
        return optionalUserPreferenceEntity.map(userPreferenceMapper::toDomain);
    }

    @Override
    public Optional<UserPreference> findByUserId(UUID userId){
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Optional<UserPreferenceEntity> optionalUserPreferenceEntity = userPreferenceJpaRepository.findByUser(userEntity);
        return optionalUserPreferenceEntity.map(userPreferenceMapper::toDomain);
    }

    @Override
    public void delete(UUID id){
        userPreferenceJpaRepository.deleteById(id);
    }

}
