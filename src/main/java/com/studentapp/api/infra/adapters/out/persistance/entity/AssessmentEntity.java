package com.studentapp.api.infra.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assessment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentEntity {

    @Id
    private UUID id;

    private String title;

    @Column(name = "assessment_date")
    private LocalDate assessmentDate;

    private Double grade;

    @Column(name = "max_grade")
    private Double maxGrade;

    private Double weight;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;
}
