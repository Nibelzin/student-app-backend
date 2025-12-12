package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UserPreference {

    private final UUID id;
    private final User user;
    private String theme;
    private String language;
    private List<Map<String, Object>> dashboardLayout;
    private Map<String, Object> settings;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserPreference(User user, String theme, String language, List<Map<String, Object>> dashboardLayout, Map<String, Object> settings) {
        this(UUID.randomUUID(), user, theme, language, dashboardLayout, settings, LocalDateTime.now(), LocalDateTime.now());
    }

    private UserPreference(UUID id, User user, String theme, String language, List<Map<String, Object>> dashboardLayout, Map<String, Object> settings, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.theme = theme;
        this.language = language;
        this.dashboardLayout = dashboardLayout;
        this.settings = settings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserPreference create(User user, String theme, String language, List<Map<String, Object>> dashboardLayout, Map<String, Object> settings) {
        return new UserPreference(user, theme, language, dashboardLayout, settings);
    }

    public static UserPreference fromState(UUID id, User user, String theme, String language, List<Map<String, Object>> dashboardLayout,Map<String, Object> settings, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new UserPreference(id, user, theme, language, dashboardLayout, settings, createdAt, updatedAt);
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        touch();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        touch();
    }

    public List<Map<String, Object>> getDashboardLayout() {
        return dashboardLayout;
    }

    public void setDashboardLayout(List<Map<String, Object>> dashboardLayout) {
        this.dashboardLayout = dashboardLayout;
        touch();
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
