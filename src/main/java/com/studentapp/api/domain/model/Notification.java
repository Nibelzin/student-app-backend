package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {

    private final UUID id;
    private NotificationType type;
    private String message;
    private boolean isRead;
    private UUID referenceId;
    private final LocalDateTime createdAt;

    private User user;

    private Notification(NotificationType type, String message, UUID referenceId, User user) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.message = message;
        this.isRead = false;
        this.referenceId = referenceId;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    private Notification(UUID id, NotificationType type, String message, boolean isRead, UUID referenceId, LocalDateTime createdAt, User user) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.referenceId = referenceId;
        this.createdAt = createdAt;
        this.user = user;
    }

    public static Notification create(User user, NotificationType type, String message, UUID referenceId) {
        return new Notification(type, message, referenceId, user);
    }

    public static Notification fromState(UUID id, NotificationType type, String message, boolean isRead, UUID referenceId, LocalDateTime createdAt, User user) {
        return new Notification(id, type, message, isRead, referenceId, createdAt, user);
    }

    public UUID getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
}
