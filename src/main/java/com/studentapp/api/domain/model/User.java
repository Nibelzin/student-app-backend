package com.studentapp.api.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

public class User implements UserDetails {

    private final UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private String course;
    private Integer currentSemester;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Period> periods = new ArrayList<>();

    private User(String name, String email, String passwordHash){
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "Name não pode ser nulo");;
        this.email = Objects.requireNonNull(email, "Email não pode ser nulo");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Password hash não pode ser nulo");
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    private User(UUID id, String name, String email, String passwordHash, LocalDateTime createdAt, LocalDateTime updatedAt, String course, Integer currentSemester, List<Period> periods) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.currentSemester = currentSemester;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.periods = periods;
    }

    public static User create(String name, String email, String passwordHash){
        return new User(name, email, passwordHash);
    }

    public static User fromState(UUID id, String name, String email, String passwordHash, String course, Integer currentSemester, LocalDateTime createdAt, LocalDateTime updatedAt, List<Period> periods) {
        return new User(id, name, email, passwordHash, createdAt, updatedAt, course, currentSemester, periods);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        touch();
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        touch();
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
        touch();
    }

    public Integer getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Integer currentSemester) {
        this.currentSemester = currentSemester;
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
        touch();
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
        period.setUser(this);
        touch();
    }

    public void removePeriod(Period period) {
        periods.remove(period);
        period.setUser(null);
        touch();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
