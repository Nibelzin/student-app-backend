package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FocusSession {

    private final UUID id;
    private int durationSeconds;
    private boolean isCompleted;
    private int xpEarned;

    private final LocalDateTime createdAt;

    private User user;
    private Subject subject;
    private Activity activity;

    private FocusSession(UUID id, int durationSeconds, boolean isCompleted, int xpEarned, LocalDateTime createdAt, User user, Subject subject, Activity activity) {
        this.id = id;
        this.durationSeconds = durationSeconds;
        this.isCompleted = isCompleted;
        this.xpEarned = xpEarned;
        this.createdAt = createdAt;
        this.user = user;
        this.subject = subject;
        this.activity = activity;
    }

    private FocusSession(int durationSeconds, boolean isCompleted, int xpEarned, User user, Subject subject, Activity activity) {
        this.id = UUID.randomUUID();
        this.durationSeconds = durationSeconds;
        this.isCompleted = isCompleted;
        this.xpEarned = xpEarned;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.subject = subject;
        this.activity = activity;
    }

    public static FocusSession create(int durationSeconds, boolean isCompleted, int xpEarned, User user, Subject subject, Activity activity) {
        return new FocusSession(durationSeconds, isCompleted, xpEarned, user, subject, activity);
    }

    public static FocusSession fromState(UUID id, int durationSeconds, boolean isCompleted, int xpEarned, LocalDateTime createdAt, User user, Subject subject, Activity activity) {
        return new FocusSession(id, durationSeconds, isCompleted, xpEarned, createdAt, user, subject, activity);
    }

    public UUID getId() {
        return id;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
