package com.studentapp.api.domain.model.assessment;

import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.domain.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Assessment {

    private final UUID id;
    private String title;
    private LocalDate assessmentDate;
    private Double grade;
    private Double maxGrade;
    private Double weight;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Subject subject;
    private User user;

    private Assessment(UUID id, String title, LocalDate assessmentDate, Double grade, Double maxGrade, Double weight, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, User user) {
        this.id = id;
        this.title = title;
        this.assessmentDate = assessmentDate;
        this.grade = grade;
        this.maxGrade = maxGrade;
        this.weight = weight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subject = subject;
        this.user = user;
    }

    private Assessment(String title, LocalDate assessmentDate, Double grade, Double maxGrade, Double weight, Subject subject, User user) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.assessmentDate = assessmentDate;
        this.grade = grade;
        this.maxGrade = maxGrade;
        this.weight = weight;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.subject = subject;
        this.user = user;
    }

    public static Assessment create(String title, LocalDate assessmentDate, Double grade, Double maxGrade, Double weight, Subject subject, User user) {
        return new Assessment(title, assessmentDate, grade, maxGrade, weight, subject, user);
    }

    public static Assessment fromState(UUID id, String title, LocalDate assessmentDate, Double grade, Double maxGrade, Double weight, LocalDateTime createdAt, LocalDateTime updatedAt, Subject subject, User user) {
        return new Assessment(id, title, assessmentDate, grade, maxGrade, weight, createdAt, updatedAt, subject, user);
    }

    public void touch() {
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

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
        touch();
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
        touch();
    }

    public Double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Double maxGrade) {
        this.maxGrade = maxGrade;
        touch();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        touch();
    }
}
