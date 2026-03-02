package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.infra.adapters.in.web.dto.notification.NotificationResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationDtoMapper {

    public NotificationResponse toResponse(Notification notification) {
        if (notification == null) return null;

        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setType(notification.getType().name());
        response.setMessage(notification.getMessage());
        response.setRead(notification.isRead());
        response.setReferenceId(notification.getReferenceId());
        response.setCreatedAt(notification.getCreatedAt());
        response.setUserId(notification.getUser().getId());

        return response;
    }
}
