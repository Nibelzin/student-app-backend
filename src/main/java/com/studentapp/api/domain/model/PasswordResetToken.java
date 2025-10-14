package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordResetToken {

    private static final int EXPIRATION_MINUTES = 60;

    private UUID id;
    private String token;
    private final LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    private LocalDateTime createdAt;

    private User user;

    private PasswordResetToken(UUID id, String token, LocalDateTime expiresAt, LocalDateTime usedAt, LocalDateTime createdAt, User user) {
        this.id = id;
        this.token = token;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
        this.createdAt = createdAt;
        this.user = user;
    }

    private PasswordResetToken(String token, User user) {
        this.id = UUID.randomUUID();
        this.token = token;
        this.expiresAt = calculateExpirationDate();
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public static PasswordResetToken create(String token, User user) {
        return new PasswordResetToken(token, user);
    }

    public static PasswordResetToken fromState(UUID id, String token, LocalDateTime expiresAt, LocalDateTime usedAt, LocalDateTime createdAt, User user) {
        return new PasswordResetToken(id, token, expiresAt, usedAt, createdAt, user);
    }

    private LocalDateTime calculateExpirationDate() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
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
