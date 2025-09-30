package com.studentapp.api.infra.adapters.in.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime createdAt;

    private List<PeriodSummaryResponse> periods;

}
