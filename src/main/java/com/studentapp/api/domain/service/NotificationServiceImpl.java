package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.domain.port.out.NotificationRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationUseCase {

    private final NotificationRepositoryPort notificationRepositoryPort;

    @Override
    public Notification createNotification(CreateNotificationData data) {
        Notification notification = Notification.create(data.user(), data.type(), data.message(), data.referenceId());
        return notificationRepositoryPort.save(notification);
    }

    @Override
    public Notification markAsRead(UUID id) {
        Notification notification = notificationRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Notificação não encontrada.")
        );
        notification.markAsRead();
        return notificationRepositoryPort.save(notification);
    }

    @Override
    public void markAllAsRead(UUID userId) {
        notificationRepositoryPort.markAllAsReadByUserId(userId);
    }

    @Override
    public Page<Notification> findByUserId(UUID userId, Pageable pageable) {
        return notificationRepositoryPort.findByUserId(userId, pageable);
    }

    @Override
    public void deleteNotification(UUID id) {
        notificationRepositoryPort.delete(id);
    }
}
