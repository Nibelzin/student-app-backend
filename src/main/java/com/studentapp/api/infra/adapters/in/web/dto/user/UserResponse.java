package com.studentapp.api.infra.adapters.in.web.dto.user;

import com.studentapp.api.domain.model.Role;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponseSummary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private String course;
    private Integer currentSemester;
    private Integer currentXp;
    private Integer currentLevel;
    private Integer coins;
    private Integer currentStreak;
    private LocalDate lastActiveDate;
    private LocalDateTime createdAt;
    private Role role;

    private List<PeriodResponseSummary> periods;

}
