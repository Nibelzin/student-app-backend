package com.studentapp.api.infra.adapters.in.web.dto.classSchedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ClassScheduleUpdateRequest {

    private Integer weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
}
