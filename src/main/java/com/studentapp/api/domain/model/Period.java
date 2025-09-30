package com.studentapp.api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Period {

    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User user;

    private Period(String name, LocalDate startDate, LocalDate endDate, Boolean isCurrent, User user) {

        if(endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("a data final não pode ser maior que a data inicial");
        }

        this.id = UUID.randomUUID();
        this.name =  Objects.requireNonNull(name, "name nao pode ser nulo");
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.user = user;
    }

    private Period(UUID id, String name, LocalDate startDate, LocalDate endDate, Boolean isCurrent, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCurrent = isCurrent;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Period create(String name, LocalDate startDate, LocalDate endDate, Boolean isCurrent, User user) {
        return new Period(name, startDate, endDate, isCurrent, user);
    }

    public static Period fromState(UUID id, String name, LocalDate startDate, LocalDate endDate, Boolean isCurrent, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new Period(id, name, startDate, endDate, isCurrent, user, createdAt, updatedAt);
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        touch();
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        touch();
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
