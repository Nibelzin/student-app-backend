package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceRequest;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserPreferenceDtoMapper {

    public UserPreference toDomain(UserPreferenceRequest request) {
        return UserPreference.create(
                null,
                request.getTheme(),
                request.getLanguage(),
                request.getDashboardLayout(),
                request.getSettings()
        );
    }

    public abstract UserPreferenceResponse toResponse(UserPreference domain);
}
