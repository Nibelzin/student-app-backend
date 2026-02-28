package com.studentapp.api.infra.adapters.out.persistance.entity;

import com.studentapp.api.domain.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name= "password_hash",nullable = false)
    private String passwordHash;

    @Column(name = "course")
    private String course;

    @Column(name = "current_semester")
    private Integer currentSemester;

    @Column(name = "current_xp")
    private Integer currentXp;

    @Column(name = "current_level")
    private Integer currentLevel;

    @Column(name = "coins")
    private Integer coins;

    @Column(name = "current_streak")
    private Integer currentStreak;

    @Column(name = "last_active_date")
    private LocalDate lastActiveDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PeriodEntity> periods = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


}
