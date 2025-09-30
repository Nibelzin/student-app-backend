package com.studentapp.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "period")
@Getter
@Setter
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private LocalDate start_date;
    private LocalDate end_date;

    private boolean is_current;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
