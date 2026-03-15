package com.studentapp.api.domain.model.focusSession;

import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.domain.model.user.User;
import com.studentapp.api.domain.model.activity.Activity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FocusSession {

    private final UUID id;
    private Integer durationSeconds;
    private boolean isCompleted;
    private int xpEarned;

    private final LocalDateTime createdAt;

    private User user;
    private Subject subject;
    private Activity activity;

    private FocusSession(UUID id, Integer durationSeconds, boolean isCompleted, int xpEarned, LocalDateTime createdAt, User user, Subject subject, Activity activity) {
        this.id = id;
        this.durationSeconds = durationSeconds;
        this.isCompleted = isCompleted;
        this.xpEarned = xpEarned;
        this.createdAt = createdAt;
        this.user = user;
        this.subject = subject;
        this.activity = activity;
    }

    private FocusSession(User user, Subject subject, Activity activity) {
        this.id = UUID.randomUUID();
        this.durationSeconds = null;
        this.isCompleted = false;
        this.xpEarned = 0;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.subject = subject;
        this.activity = activity;
    }

    public static FocusSession create(User user, Subject subject, Activity activity) {
        return new FocusSession(user, subject, activity);
    }

    public static FocusSession fromState(UUID id, Integer durationSeconds, boolean isCompleted, int xpEarned, LocalDateTime createdAt, User user, Subject subject, Activity activity) {
        return new FocusSession(id, durationSeconds, isCompleted, xpEarned, createdAt, user, subject, activity);
    }

    public UUID getId() {
        return id;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
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
