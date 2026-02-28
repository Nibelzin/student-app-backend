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
    private Activity activity;

    private FileObject fileObject;

    private Material(UUID id, String title, String type, String externalUrl, Boolean isFavorite, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, Activity activity, FileObject fileObject) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.externalUrl = externalUrl;
        this.isFavorite = isFavorite;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subject = subject;
        this.activity = activity;
        this.fileObject = fileObject;
    }

    private Material(String title, String type, String externalUrl, Boolean isFavorite, Subject subject, Activity activity, FileObject fileObject) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.type = type;
        this.externalUrl = externalUrl;
        this.isFavorite = isFavorite;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.subject = subject;
        this.activity = activity;
        this.fileObject = fileObject;
    }

    public static Material create(String title, String type, String externalUrl, Boolean isFavorite, Subject subject, Activity activity, FileObject fileObject) {
        return new Material(title, type, externalUrl, isFavorite, subject, activity, fileObject);
    }

    public static Material fromState(UUID id, String title, String type, String externalUrl, Boolean isFavorite, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, Activity activity, FileObject fileObject) {
        return new Material(id, title, type, externalUrl, isFavorite, createdAt, updatedAt, subject, activity, fileObject);
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

    public Activity getActivity() {
        return activity;
    };

    public void setActivity(Activity activity) {
        this.activity = activity;
        touch();
    }

    public FileObject getFile() {
        return fileObject;
    }

    public void setFile(FileObject fileObject) {
        this.fileObject = fileObject;
        touch();
    }
}
