package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Material {

    private final UUID id;
    private String title;
    private String type;
    private String externalUrl;
    private Boolean isFavorite;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Subject subject;
    private File file;

    private Material(UUID id, String title, String type, String externalUrl, Boolean isFavorite, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, File file) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.externalUrl = externalUrl;
        this.isFavorite = isFavorite;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subject = subject;
        this.file = file;
    }

    private Material(String title, String type, String externalUrl, Boolean isFavorite, Subject subject, File file) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.type = type;
        this.externalUrl = externalUrl;
        this.isFavorite = isFavorite;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.subject = subject;
        this.file = file;
    }

    public static Material create(String title, String type, String externalUrl, Boolean isFavorite, Subject subject, File file) {
        return new Material(title, type, externalUrl, isFavorite, subject, file);
    }

    public static Material fromState(UUID id, String title, String type, String externalUrl, Boolean isFavorite, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, File file) {
        return new Material(id, title, type, externalUrl, isFavorite, createdAt, updatedAt, subject, file);
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        touch();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        touch();
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
        touch();
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        touch();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        touch();
    }
}
