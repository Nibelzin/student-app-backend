package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.UserPreference;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceUseCase {

    record UserPreferenceUpdateData(String theme, String language, List<Map<String, Object>> dashboardLayout, Map<String, Object> settings) {}

    UserPreference createUserPreference(String theme, String language, List<Map<String, Object>> dashboardLayout, Map<String, Object> settings, UUID userId);

    UserPreference updateUserPreference(UUID id, UserPreferenceUpdateData userPreferenceUpdateData);

    Optional<UserPreference> findUserPreferenceById(UUID id);

    UserPreference findUserPreferenceByUserId(UUID userId);

    void deleteUserPreference(UUID id);

}
