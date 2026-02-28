package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Activity {

    private final UUID id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Boolean isCompleted;
    private String type;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Subject subject;
    private List<ChecklistItem> checklist;

    private Activity(UUID id, String title, String description, LocalDateTime dueDate, Boolean isCompleted, String type, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, List<ChecklistItem> checklist){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subject = subject;
        this.checklist = checklist;
    }

    private Activity(String title, String description, LocalDateTime dueDate, Boolean isCompleted, String type, Subject subject, List<ChecklistItem> checklist){
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.subject = subject;
        this.checklist = checklist;
    }

    public static Activity create(String title, String description, LocalDateTime dueDate, Boolean isCompleted, String type, Subject subject, List<ChecklistItem> checklist){
        return new Activity(title, description, dueDate, isCompleted, type, subject, checklist);
    }

    public static Activity fromState(UUID id, String title, String description, LocalDateTime dueDate, Boolean isCompleted, String type, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, List<ChecklistItem> checklist){
        return new Activity(id, title, description, dueDate, isCompleted, type, createdAt, updatedAt, subject, checklist);
    }

    private void touch(){
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue(){

        if (this.isCompleted){
            return false;
        }

        if (this.dueDate == null){
            return false;
        }

        return this.dueDate.isBefore(LocalDateTime.now());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        touch();
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        touch();
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
        touch();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<ChecklistItem> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<ChecklistItem> checklist) {
        this.checklist = checklist;
        touch();
    }
}
