package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.domain.port.in.UserPreferenceUseCase;
import com.studentapp.api.domain.port.out.UserPreferenceRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceUseCase {

    private final UserPreferenceRepositoryPort userPreferenceRepository;
    private final UserRepositoryPort userRepository;

    @Override
    public UserPreference createUserPreference(String theme, String language, List<Map<String, Object>> dashboardLayout, Map<String, Object> settings, UUID userId){
        if (userId == null){
            throw new IllegalArgumentException("O ID do usuário é obrigatório para criar suas preferencias");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Optional<UserPreference> existingUserPreference = userPreferenceRepository.findByUserId(userId);

        if(existingUserPreference.isPresent()){
            return existingUserPreference.get();
        }

        UserPreference newUserPreference = UserPreference.create(user, theme, language, dashboardLayout, settings);

        return userPreferenceRepository.save(newUserPreference);

    }

    @Override
    public UserPreference updateUserPreference(UUID id, UserPreferenceUpdateData userPreferenceUpdateData){

        UserPreference existingUserPreference = userPreferenceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Preferencia de usuário não encontrada.")
        );

        if (userPreferenceUpdateData.theme() != null && !userPreferenceUpdateData.theme().isBlank()){
            existingUserPreference.setTheme(userPreferenceUpdateData.theme());
        }

        if (userPreferenceUpdateData.language() != null && !userPreferenceUpdateData.language().isBlank()){
            existingUserPreference.setLanguage(userPreferenceUpdateData.language());
        }

        if (userPreferenceUpdateData.dashboardLayout() != null){
            existingUserPreference.setDashboardLayout(userPreferenceUpdateData.dashboardLayout());
        }

        if(userPreferenceUpdateData.settings() != null){
            existingUserPreference.setSettings(userPreferenceUpdateData.settings());
        }

        return userPreferenceRepository.save(existingUserPreference);
    }

    @Override
    public Optional<UserPreference> findUserPreferenceById(UUID id){
        return userPreferenceRepository.findById(id);
    }

    @Override
    public UserPreference findUserPreferenceByUserId(UUID userId){

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        return userPreferenceRepository.findByUserId(userId).orElseGet(() -> {
            UserPreference newUserPreference = UserPreference.create(
                    user,
                    null,
                    null,
                    null,
                    null
            );

            return userPreferenceRepository.save(newUserPreference);
        });
    }

    @Override
    public void deleteUserPreference(UUID id){
        userPreferenceRepository.delete(id);
    }

}
