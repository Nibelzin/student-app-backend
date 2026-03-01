package com.studentapp.api.infra.adapters.in.web.dto.classSchedule;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ClassScheduleCreateRequest {

    @NotNull
    private Integer weekDay;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private String location;

    @NotNull
    private UUID subjectId;
}
