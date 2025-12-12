package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.UserPreference;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepositoryPort {

    UserPreference save(UserPreference userPreference);

    Optional<UserPreference> findById(UUID id);
    Optional<UserPreference> findByUserId(UUID userId);

    void delete(UUID id);


}
