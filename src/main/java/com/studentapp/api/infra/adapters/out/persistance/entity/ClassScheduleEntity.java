package com.studentapp.api.infra.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "class_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassScheduleEntity {

    @Id
    private UUID id;

    @Column(name = "week_day")
    private Integer weekDay;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;
}
