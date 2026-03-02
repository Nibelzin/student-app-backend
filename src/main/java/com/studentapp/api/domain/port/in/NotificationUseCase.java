package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationUseCase {

    record CreateNotificationData(User user, NotificationType type, String message, UUID referenceId) {}

    Notification createNotification(CreateNotificationData data);

    Notification markAsRead(UUID id);

    void markAllAsRead(UUID userId);

    Page<Notification> findByUserId(UUID userId, Pageable pageable);

    void deleteNotification(UUID id);
}
