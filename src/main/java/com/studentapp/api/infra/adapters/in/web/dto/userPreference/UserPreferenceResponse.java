package com.studentapp.api.infra.adapters.in.web.dto.userPreference;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class UserPreferenceResponse {

    private UUID id;
    private String theme;
    private String language;

    private Map<String, Object> settings;
    private List<Map<String, Object>> dashboardLayout;

}
