package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reminder {

    private final UUID id;
    private String title;
    private LocalDateTime triggerAt;
    private Boolean isSent;
    private String channel;

    private final LocalDateTime createdAt;

    private User user;
    private Subject subject;
    private Activity activity;
    private Note note;
    private PlannerEvent plannerEvent;

    private Reminder(UUID id, String title, LocalDateTime triggerAt, Boolean isSent, String channel, LocalDateTime createdAt, User user, Subject subject, Activity activity, Note note, PlannerEvent plannerEvent) {
        this.id = id;
        this.title = title;
        this.triggerAt = triggerAt;
        this.isSent = isSent;
        this.channel = channel;
        this.createdAt = createdAt;
        this.user = user;
        this.subject = subject;
        this.activity = activity;
        this.note = note;
        this.plannerEvent = plannerEvent;
    }

    private Reminder(String title, LocalDateTime triggerAt, Boolean isSent, String channel, User user, Subject subject, Activity activity, Note note, PlannerEvent plannerEvent) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.triggerAt = triggerAt;
        this.isSent = isSent;
        this.channel = channel;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.subject = subject;
        this.activity = activity;
        this.note = note;
        this.plannerEvent = plannerEvent;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getTriggerAt() {
        return triggerAt;
    }

    public void setTriggerAt(LocalDateTime triggerAt) {
        this.triggerAt = triggerAt;
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public PlannerEvent getPlannerEvent() {
        return plannerEvent;
    }

    public void setPlannerEvent(PlannerEvent plannerEvent) {
        this.plannerEvent = plannerEvent;
    }
}
