package com.studentapp.api.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class User implements UserDetails {

    private final UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private String course;
    private Integer currentSemester;
    private Integer currentXp;
    private Integer currentLevel;
    private Integer coins;
    private Integer currentStreak;
    private LocalDate lastActiveDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Period> periods = new ArrayList<>();

    private Role role;

    private User(String name, String email, String passwordHash, Role role) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "Name não pode ser nulo");
        this.email = Objects.requireNonNull(email, "Email não pode ser nulo");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Password hash não pode ser nulo");
        this.currentXp = 0;
        this.currentLevel = 1;
        this.coins = 0;
        this.currentStreak = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.role = role;
    }

    private User(UUID id, String name, String email, String passwordHash, String course,
                 Integer currentSemester, Integer currentXp, Integer currentLevel,
                 Integer coins, Integer currentStreak, LocalDate lastActiveDate,
                 LocalDateTime createdAt, LocalDateTime updatedAt, List<Period> periods, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.course = course;
        this.currentSemester = currentSemester;
        this.currentXp = currentXp;
        this.currentLevel = currentLevel;
        this.coins = coins;
        this.currentStreak = currentStreak;
        this.lastActiveDate = lastActiveDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.periods = periods;
        this.role = role;
    }

    public static User create(String name, String email, String passwordHash, Role role) {
        return new User(name, email, passwordHash, role);
    }

    public static User fromState(UUID id, String name, String email, String passwordHash,
                                 String course, Integer currentSemester, Integer currentXp,
                                 Integer currentLevel, Integer coins, Integer currentStreak,
                                 LocalDate lastActiveDate, LocalDateTime createdAt,
                                 LocalDateTime updatedAt, List<Period> periods, Role role) {
        return new User(id, name, email, passwordHash, course, currentSemester, currentXp,
                currentLevel, coins, currentStreak, lastActiveDate, createdAt, updatedAt, periods, role);
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

    public Integer getCurrentXp() {
        return currentXp;
    }

    public void setCurrentXp(Integer currentXp) {
        this.currentXp = currentXp;
        touch();
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
        touch();
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
        touch();
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
        touch();
    }

    public LocalDate getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(LocalDate lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
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
