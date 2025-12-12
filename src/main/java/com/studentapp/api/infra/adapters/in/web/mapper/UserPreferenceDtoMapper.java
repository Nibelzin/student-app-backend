package com.studentapp.api.infra.adapters.in.web.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceRequest;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserPreferenceDtoMapper {

    private final ObjectMapper objectMapper;

    public UserPreference toDomain(UserPreferenceRequest request){
        String layoutJson = null;

        return UserPreference.create(
                null,
                request.getTheme(),
                request.getLanguage(),
                request.getDashboardLayout(),
                request.getSettings()
        );
    }

    public UserPreferenceResponse toResponse(UserPreference domain) {
        UserPreferenceResponse response = new UserPreferenceResponse();
        response.setId(domain.getId());
        response.setTheme(domain.getTheme());
        response.setLanguage(domain.getLanguage());
        response.setSettings(domain.getSettings());
        response.setDashboardLayout(domain.getDashboardLayout());

        return response;
    }

}
