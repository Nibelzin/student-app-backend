package com.studentapp.api.domain.model;

import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

public class ClassSchedule {

    private final UUID id;
    private Integer weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    private Subject subject;

    private ClassSchedule(UUID id, Integer weekDay, LocalTime startTime, LocalTime endTime, String location, Subject subject) {
        this.id = id;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.subject = subject;
    }

    private ClassSchedule(Integer weekDay, LocalTime startTime, LocalTime endTime, String location, Subject subject) {
        this.id = UUID.randomUUID();
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.subject = subject;
    }

    public static ClassSchedule create(Integer weekDay, LocalTime startTime, LocalTime endTime, String location, Subject subject) {
        return new ClassSchedule(weekDay, startTime, endTime, location, subject);
    }

    public static ClassSchedule fromState(UUID id, Integer weekDay, LocalTime startTime, LocalTime endTime, String location, Subject subject) {
        return new ClassSchedule(id, weekDay, startTime, endTime, location, subject);
    }

    public UUID getId() {
        return id;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
