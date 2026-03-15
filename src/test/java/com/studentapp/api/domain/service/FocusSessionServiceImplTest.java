package com.studentapp.api.domain.service;

import com.studentapp.api.domain.GamificationConfig;
import com.studentapp.api.domain.enums.NotificationType;
import com.studentapp.api.domain.enums.Role;
import com.studentapp.api.domain.model.focusSession.FocusSession;
import com.studentapp.api.domain.model.user.User;
import com.studentapp.api.domain.port.in.FocusSessionUseCase;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.domain.port.out.FocusSessionRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FocusSessionServiceImplTest {

    @Mock FocusSessionRepositoryPort focusSessionRepositoryPort;
    @Mock UserRepositoryPort userRepositoryPort;
    @Mock SubjectRepositoryPort subjectRepositoryPort;
    @Mock ActivityRepositoryPort activityRepositoryPort;
    @Mock NotificationUseCase notificationUseCase;

    @InjectMocks FocusSessionServiceImpl focusSessionService;

    private User buildUser(int currentXp, int currentLevel) {
        return User.fromState(
                UUID.randomUUID(), "Test", "test@example.com", "hash",
                null, null, currentXp, currentLevel, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
    }

    private FocusSession buildSession(User user, Integer durationSeconds, boolean isCompleted, int xpEarned) {
        return FocusSession.fromState(
                UUID.randomUUID(), durationSeconds, isCompleted, xpEarned,
                LocalDateTime.now(), user, null, null
        );
    }

    @Test
    void createFocusSession_createsSessionWithDefaults() {
        User user = buildUser(0, 1);
        when(userRepositoryPort.findById(user.getId())).thenReturn(Optional.of(user));
        when(focusSessionRepositoryPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        FocusSessionUseCase.CreateFocusSessionData data =
                new FocusSessionUseCase.CreateFocusSessionData(user.getId(), null, null);

        FocusSession result = focusSessionService.createFocusSession(data);

        assertThat(result.isCompleted()).isFalse();
        assertThat(result.getDurationSeconds()).isNull();
        assertThat(result.getXpEarned()).isEqualTo(0);
        verify(userRepositoryPort, never()).save(any());
        verify(notificationUseCase, never()).createNotification(any());
    }

    @Test
    void createFocusSession_throwsWhenUserNotFound() {
        UUID unknownId = UUID.randomUUID();
        when(userRepositoryPort.findById(unknownId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> focusSessionService.createFocusSession(
                new FocusSessionUseCase.CreateFocusSessionData(unknownId, null, null)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateFocusSession_completingSession_awardsXpAndSaves() {
        User user = buildUser(0, 1);
        FocusSession session = buildSession(user, null, false, 0);
        when(focusSessionRepositoryPort.findById(session.getId())).thenReturn(Optional.of(session));
        when(focusSessionRepositoryPort.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(userRepositoryPort.save(any())).thenReturn(user);

        FocusSessionUseCase.UpdateFocusSessionData data =
                new FocusSessionUseCase.UpdateFocusSessionData(1800, true);

        FocusSession result = focusSessionService.updateFocusSession(session.getId(), data);

        assertThat(result.isCompleted()).isTrue();
        assertThat(result.getDurationSeconds()).isEqualTo(1800);
        assertThat(result.getXpEarned()).isEqualTo(GamificationConfig.XP_FOCUS_PER_MINUTE * 30);
        verify(userRepositoryPort).save(user);
    }

    @Test
    void updateFocusSession_completingSession_sendsLevelUpNotification() {
        User user = buildUser(99, 1);
        FocusSession session = buildSession(user, null, false, 0);
        when(focusSessionRepositoryPort.findById(session.getId())).thenReturn(Optional.of(session));
        when(focusSessionRepositoryPort.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(userRepositoryPort.save(any())).thenReturn(user);

        focusSessionService.updateFocusSession(session.getId(),
                new FocusSessionUseCase.UpdateFocusSessionData(60, true));

        ArgumentCaptor<NotificationUseCase.CreateNotificationData> captor =
                ArgumentCaptor.forClass(NotificationUseCase.CreateNotificationData.class);
        verify(notificationUseCase).createNotification(captor.capture());
        assertThat(captor.getValue().type()).isEqualTo(NotificationType.LEVEL_UP);
    }

    @Test
    void updateFocusSession_alreadyCompleted_doesNotAwardXpAgain() {
        User user = buildUser(50, 1);
        FocusSession session = buildSession(user, 1800, true, 30);
        when(focusSessionRepositoryPort.findById(session.getId())).thenReturn(Optional.of(session));
        when(focusSessionRepositoryPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        focusSessionService.updateFocusSession(session.getId(),
                new FocusSessionUseCase.UpdateFocusSessionData(null, true));

        verify(userRepositoryPort, never()).save(any());
        verify(notificationUseCase, never()).createNotification(any());
    }

    @Test
    void awardTickXp_awardsXpToUser() {
        User user = buildUser(0, 1);
        when(userRepositoryPort.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepositoryPort.save(any(User.class))).thenReturn(user);

        FocusSessionUseCase.FocusSessionTickResult result = focusSessionService.awardTickXp(user.getId());

        verify(userRepositoryPort).save(user);
        assertThat(result.currentXp()).isEqualTo(GamificationConfig.XP_FOCUS_TICK);
        assertThat(result.currentLevel()).isEqualTo(1);
        assertThat(result.leveledUp()).isFalse();
    }

    @Test
    void awardTickXp_sendsLevelUpNotification_whenLeveledUp() {
        // User at 99 XP, level 1 — threshold is 100, so one tick (2 XP) pushes them to level 2
        User user = buildUser(99, 1);
        when(userRepositoryPort.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepositoryPort.save(any(User.class))).thenReturn(user);

        FocusSessionUseCase.FocusSessionTickResult result = focusSessionService.awardTickXp(user.getId());

        assertThat(result.leveledUp()).isTrue();
        assertThat(result.currentLevel()).isEqualTo(2);

        ArgumentCaptor<NotificationUseCase.CreateNotificationData> captor =
                ArgumentCaptor.forClass(NotificationUseCase.CreateNotificationData.class);
        verify(notificationUseCase).createNotification(captor.capture());
        NotificationUseCase.CreateNotificationData notifData = captor.getValue();
        assertThat(notifData.user()).isEqualTo(user);
        assertThat(notifData.type()).isEqualTo(NotificationType.LEVEL_UP);
    }

    @Test
    void awardTickXp_throwsWhenUserNotFound() {
        UUID unknownId = UUID.randomUUID();
        when(userRepositoryPort.findById(unknownId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> focusSessionService.awardTickXp(unknownId))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(userRepositoryPort, never()).save(any());
        verify(notificationUseCase, never()).createNotification(any());
    }
}
