package com.studentapp.api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AttendanceLog {
    
    private final UUID id;
    private LocalDate classDate;
    private Integer classesScheduled;
    private Integer absences;
    private String notes;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Subject subject;
    
    private AttendanceLog(UUID id, LocalDate classDate, Integer classesScheduled, Integer absences, String notes, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject) {
        this.id = id;
        this.classDate = classDate;
        this.classesScheduled = classesScheduled;
        this.absences = absences;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subject = subject;
    }
    
    private AttendanceLog(LocalDate classDate, Integer classesScheduled, Integer absences, String notes, Subject subject) {
        this.id = UUID.randomUUID();
        this.classDate = classDate;
        this.classesScheduled = classesScheduled;
        this.absences = absences;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.subject = subject;
    }
    
    public static AttendanceLog create(LocalDate classDate, Integer classesScheduled, Integer absences, String notes, Subject subject) {
        return new AttendanceLog(classDate, classesScheduled, absences, notes, subject);
    }
    
    public static AttendanceLog fromState(UUID id, LocalDate classDate, Integer classesScheduled, Integer absences, String notes, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject) {
        return new AttendanceLog(id, classDate, classesScheduled, absences, notes, createdAt, updatedAt, subject);
    }
    
    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
        touch();
    }

    public Integer getClassesScheduled() {
        return classesScheduled;
    }

    public void setClassesScheduled(Integer classesScheduled) {
        this.classesScheduled = classesScheduled;
        touch();
    }

    public Integer getAbsences() {
        return absences;
    }

    public void setAbsences(Integer absences) {
        this.absences = absences;
        touch();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}
