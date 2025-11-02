package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlannerEvent {

    private final UUID id;
    private String title;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private Boolean allDay;
    private String rule;

    private String color;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User user;
    private Subject subject;
    private Activity activity;

    private PlannerEvent(UUID id, String title, LocalDateTime startAt, LocalDateTime endAt, Boolean allDay, String rule, String color, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Subject subject, Activity activity) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.allDay = allDay;
        this.rule = rule;
        this.color = color;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.subject = subject;
        this.activity = activity;
    }

    private PlannerEvent(String title, LocalDateTime startAt, LocalDateTime endAt, Boolean allDay, String rule, String color, User user, Subject subject, Activity activity) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.allDay = allDay;
        this.rule = rule;
        this.color = color;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.user = user;
        this.subject = subject;
        this.activity = activity;
    }

    public static PlannerEvent create(String title, LocalDateTime startAt, LocalDateTime endAt, Boolean allDay, String rule, String color, User user, Subject subject, Activity activity) {
        return new PlannerEvent(title, startAt, endAt, allDay, rule, color, user, subject, activity);
    }

    public static PlannerEvent fromState(UUID id, String title, LocalDateTime startAt, LocalDateTime endAt, Boolean allDay, String rule, String color, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Subject subject, Activity activity) {
        return new PlannerEvent(id, title, startAt, endAt, allDay, rule, color, createdAt, updatedAt, user, subject, activity);
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

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
        touch();
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
        touch();
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
        touch();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
        touch();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        touch();
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
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        touch();
    }
}
