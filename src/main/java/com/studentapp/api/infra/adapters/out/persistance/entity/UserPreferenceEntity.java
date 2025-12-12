package com.studentapp.api.infra.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "user_preference")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceEntity {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "theme")
    private String theme;

    @Column(name = "language")
    private String language;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dashboard_layout", columnDefinition = "jsonb")
    private List<Map<String, Object>> dashboardLayout;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "settings", columnDefinition = "jsonb")
    private Map<String, Object> settings;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
