package com.studentapp.api.infra.adapters.in.web.dto.userPreference;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserPreferenceRequest {

    private String theme;
    private String language;

    private Map<String, Object> settings;
    private List<Map<String, Object>> dashboardLayout;

}
