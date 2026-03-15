package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.activity.Activity;
import com.studentapp.api.domain.model.focusSession.FocusSession;
import com.studentapp.api.domain.GamificationConfig;
import com.studentapp.api.domain.enums.NotificationType;
import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.domain.model.user.User;
import com.studentapp.api.domain.port.in.FocusSessionUseCase;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.domain.port.out.FocusSessionRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FocusSessionServiceImpl implements FocusSessionUseCase {

    private final FocusSessionRepositoryPort focusSessionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final SubjectRepositoryPort subjectRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final NotificationUseCase notificationUseCase;

    private int calculateXp(Integer durationSeconds, boolean isCompleted) {
        if (!isCompleted || durationSeconds == null) return 0;
        return Math.max(GamificationConfig.XP_FOCUS_PER_MINUTE, (durationSeconds / 60) * GamificationConfig.XP_FOCUS_PER_MINUTE);
    }

    @Override
    public FocusSession createFocusSession(CreateFocusSessionData data) {
        User user = userRepositoryPort.findById(data.userId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        Subject subject = null;
        if (data.subjectId() != null) {
            subject = subjectRepositoryPort.findById(data.subjectId()).orElseThrow(
                    () -> new ResourceNotFoundException("Matéria não encontrada.")
            );
        }

        Activity activity = null;
        if (data.activityId() != null) {
            activity = activityRepositoryPort.findById(data.activityId()).orElseThrow(
                    () -> new ResourceNotFoundException("Atividade não encontrada.")
            );
        }

        FocusSession newSession = FocusSession.create(user, subject, activity);
        return focusSessionRepositoryPort.save(newSession);
    }

    @Override
    public FocusSession updateFocusSession(UUID id, UpdateFocusSessionData data) {
        FocusSession existing = focusSessionRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sessão de foco não encontrada.")
        );

        boolean wasCompleted = existing.isCompleted();

        if (data.durationSeconds() != null) {
            if (existing.getDurationSeconds() != null && data.durationSeconds() < existing.getDurationSeconds()) {
                throw new IllegalArgumentException("Não é possível diminuir a duração de uma sessão de foco.");
            }
            existing.setDurationSeconds(data.durationSeconds());
        }

        if (data.isCompleted() != null) {
            existing.setCompleted(data.isCompleted());
        }

        int xpEarned = calculateXp(existing.getDurationSeconds(), existing.isCompleted());
        existing.setXpEarned(xpEarned);

        FocusSession saved = focusSessionRepositoryPort.save(existing);

        boolean nowCompleted = saved.isCompleted();
        if (!wasCompleted && nowCompleted && xpEarned > 0) {
            User user = saved.getUser();
            boolean leveledUp = user.awardXp(xpEarned);
            userRepositoryPort.save(user);
            if (leveledUp) {
                notificationUseCase.createNotification(new NotificationUseCase.CreateNotificationData(
                        user,
                        NotificationType.LEVEL_UP,
                        "Você avançou para o nível " + user.getCurrentLevel() + "!",
                        null
                ));
            }
        }

        return saved;
    }

    @Override
    public Optional<FocusSession> findFocusSessionById(UUID id) {
        return focusSessionRepositoryPort.findById(id);
    }

    @Override
    public Page<FocusSession> findByQuery(FocusSessionQueryData query, Pageable pageable) {
        return focusSessionRepositoryPort.findByQuery(query, pageable);
    }

    @Override
    public void deleteFocusSession(UUID id) {
        focusSessionRepositoryPort.delete(id);
    }

    @Override
    public FocusSessionTickResult awardTickXp(UUID userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        boolean leveledUp = user.awardXp(GamificationConfig.XP_FOCUS_TICK);
        userRepositoryPort.save(user);
        if (leveledUp) {
            notificationUseCase.createNotification(new NotificationUseCase.CreateNotificationData(
                    user,
                    NotificationType.LEVEL_UP,
                    "Você avançou para o nível " + user.getCurrentLevel() + "!",
                    null
            ));
        }
        return new FocusSessionTickResult(user.getCurrentXp(), user.getCurrentLevel(), leveledUp);
    }
}
