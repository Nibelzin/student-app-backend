package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.model.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);
    Optional<Notification> findById(UUID id);
    Page<Notification> findByUserId(UUID userId, Pageable pageable);
    void delete(UUID id);
    void markAllAsReadByUserId(UUID userId);
    boolean existsUnreadByUserIdAndTypeAndReferenceId(UUID userId, NotificationType type, UUID referenceId);
}
