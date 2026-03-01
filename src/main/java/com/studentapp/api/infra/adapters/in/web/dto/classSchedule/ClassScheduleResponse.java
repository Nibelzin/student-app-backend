package com.studentapp.api.infra.adapters.in.web.dto.classSchedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ClassScheduleResponse {

    private UUID id;
    private Integer weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private UUID subjectId;
    private String subjectName;
}
