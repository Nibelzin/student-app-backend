package com.studentapp.api.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Subject {

    private UUID id;
    private String name;
    private String professor;
    private String classroom;
    private String color;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User user;
    private Period period;

    private Subject(String name, String professor, String classroom, String color, User user, Period period) {

        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "name nao pode ser nulo");
        this.professor = professor;
        this.classroom = classroom;
        this.color = color;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;

        this.user = user;
        this.period = period;

    }

    private Subject(UUID id, String name, String professor, String classroom, String color, User user, Period period, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.professor = professor;
        this.classroom = classroom;
        this.color = color;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.period = period;

    }

    public static Subject create(String name, String professor, String classroom, String color, User user, Period period) {
        return new Subject(name, professor, classroom, color, user, period);
    }

    public static Subject fromState(UUID id, String name, String professor, String classroom, String color, User user, Period period, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Subject(id, name, professor, classroom, color, user, period, createdAt, updatedAt);
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        touch();
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
        touch();
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
        touch();
    }
}
