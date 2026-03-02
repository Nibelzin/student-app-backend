package com.studentapp.api.infra.adapters.in.web.dto.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NotificationResponse {

    private UUID id;
    private String type;
    private String message;
    private boolean isRead;
    private UUID referenceId;
    private LocalDateTime createdAt;
    private UUID userId;
}
