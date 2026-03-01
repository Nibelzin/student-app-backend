package com.studentapp.api.infra.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "focus_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FocusSessionEntity {

    @Id
    private UUID id;

    @Column(name = "duration_seconds")
    private int durationSeconds;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "xp_earned")
    private int xpEarned;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activity;
}
