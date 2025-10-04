package com.studentapp.api.infra.adapters.in.web.dto.user;

import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponse;
import lombok.Getter;
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

    private List<PeriodResponse> periods;

}
