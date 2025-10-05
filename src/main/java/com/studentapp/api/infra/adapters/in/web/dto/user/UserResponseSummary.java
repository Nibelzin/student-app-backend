package com.studentapp.api.infra.adapters.in.web.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserResponseSummary {

    private UUID id;
    private String name;
    private String email;
    private String course;
    private Integer currentSemester;
    private LocalDateTime createdAt;

}
