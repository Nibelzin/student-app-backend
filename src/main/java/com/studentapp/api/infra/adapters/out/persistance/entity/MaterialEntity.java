package com.studentapp.api.infra.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity {

    @Id
    private UUID id;

    @Column
    private String title;

    @Column
    private String type;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "is_favorite")
    private Boolean isFavorite;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activity;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private FileObjectEntity file;

}
