package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.model.Role;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.domain.port.out.NotificationRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    NotificationRepositoryPort notificationRepositoryPort;

    @InjectMocks
    NotificationServiceImpl notificationService;

    private User buildUser() {
        return User.fromState(
                UUID.randomUUID(), "Test", "test@example.com", "hash",
                null, null, 0, 1, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
    }

    private Notification buildNotification(User user) {
        return Notification.create(user, NotificationType.LEVEL_UP, "Você avançou para o nível 2!", null);
    }

    @Test
    void createNotification_savesAndReturnsNotification() {
        User user = buildUser();
        Notification notification = buildNotification(user);
        when(notificationRepositoryPort.save(any(Notification.class))).thenReturn(notification);

        NotificationUseCase.CreateNotificationData data = new NotificationUseCase.CreateNotificationData(
                user, NotificationType.LEVEL_UP, "Você avançou para o nível 2!", null
        );

        Notification result = notificationService.createNotification(data);

        assertThat(result).isEqualTo(notification);
        verify(notificationRepositoryPort).save(any(Notification.class));
    }

    @Test
    void markAsRead_updatesIsReadFlag() {
        User user = buildUser();
        Notification notification = buildNotification(user);
        UUID id = notification.getId();

        when(notificationRepositoryPort.findById(id)).thenReturn(Optional.of(notification));
        when(notificationRepositoryPort.save(any(Notification.class))).thenAnswer(inv -> inv.getArgument(0));

        Notification result = notificationService.markAsRead(id);

        assertThat(result.isRead()).isTrue();
        verify(notificationRepositoryPort).save(notification);
    }

    @Test
    void markAsRead_throwsResourceNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(notificationRepositoryPort.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationService.markAsRead(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void markAllAsRead_delegatesToRepository() {
        UUID userId = UUID.randomUUID();

        notificationService.markAllAsRead(userId);

        verify(notificationRepositoryPort).markAllAsReadByUserId(userId);
    }

    @Test
    void findByUserId_returnsPagedNotifications() {
        User user = buildUser();
        Notification notification = buildNotification(user);
        Page<Notification> page = new PageImpl<>(List.of(notification));

        when(notificationRepositoryPort.findByUserId(user.getId(), Pageable.unpaged())).thenReturn(page);

        Page<Notification> result = notificationService.findByUserId(user.getId(), Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(notification);
    }

    @Test
    void deleteNotification_delegatesToRepository() {
        UUID id = UUID.randomUUID();

        notificationService.deleteNotification(id);

        verify(notificationRepositoryPort).delete(id);
    }
}
