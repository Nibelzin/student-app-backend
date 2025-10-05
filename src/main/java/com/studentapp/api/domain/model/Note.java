package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Note {

    private final UUID id;
    private String content;
    private boolean isPinned;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    private User user;

    private Note(String content, boolean isPinned, User user) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.isPinned = isPinned;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.user = user;
    }

    private Note(UUID id, String content, boolean isPinned, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.isPinned = isPinned;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Note create(String content, boolean isPinned, User user) {
        return new Note(content, isPinned, user);
    }

    public static Note fromState(UUID id, String content, boolean isPinned, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Note(id, content, isPinned, user, createdAt, updatedAt);
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        touch();
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        touch();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
