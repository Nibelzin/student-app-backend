package com.studentapp.api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AbsenceLog {
    
    private final UUID id;
    private LocalDate absenceDate;
    private String notes;
    
    private final LocalDateTime createdAt;
    
    private Subject subject;
    
    private AbsenceLog(
            UUID id,
            LocalDate absenceDate,
            String notes,
            LocalDateTime createdAt,
            Subject subject) {
        this.id = id;
        this.notes = notes;
        this.absenceDate = absenceDate;
        this.createdAt = createdAt;
        this.subject = subject;
    }
    
    private AbsenceLog(
            LocalDate absenceDate,
            String notes,
            Subject subject) {
        this.id = UUID.randomUUID();
        this.absenceDate = absenceDate;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
        this.subject = subject;
    }
    
    public static AbsenceLog create(LocalDate absenceDate, String notes, Subject subject) {
        return new AbsenceLog(absenceDate, notes, subject);
    }
    
    public static AbsenceLog fromState(UUID id, LocalDate absenceDate, String notes, LocalDateTime createdAt, Subject subject) {
        return new AbsenceLog(id, absenceDate, notes, createdAt, subject);
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(LocalDate absenceDate) {
        this.absenceDate = absenceDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
