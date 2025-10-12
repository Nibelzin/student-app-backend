package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordResetToken {

    private final UUID id;
    private final String token;
    private final LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    private final LocalDateTime createdAt;

    private User user;

    public PasswordResetToken(String token, LocalDateTime expiresAt, User user) {
        this.id = UUID.randomUUID();
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
