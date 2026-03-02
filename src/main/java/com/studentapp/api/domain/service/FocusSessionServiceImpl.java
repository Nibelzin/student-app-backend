package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.domain.model.GamificationConfig;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
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

    private int calculateXp(int durationSeconds, boolean isCompleted) {
        return isCompleted ? Math.max(GamificationConfig.XP_FOCUS_PER_MINUTE, (durationSeconds / 60) * GamificationConfig.XP_FOCUS_PER_MINUTE) : 0;
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

        int xpEarned = calculateXp(data.durationSeconds(), data.isCompleted());
        FocusSession newSession = FocusSession.create(data.durationSeconds(), data.isCompleted(), xpEarned, user, subject, activity);

        FocusSession saved = focusSessionRepositoryPort.save(newSession);

        if (data.isCompleted() && xpEarned > 0) {
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
    public FocusSession updateFocusSession(UUID id, UpdateFocusSessionData data) {
        FocusSession existing = focusSessionRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sessão de foco não encontrada.")
        );

        boolean wasCompleted = existing.isCompleted();

        if (data.durationSeconds() != null) {
            if (data.durationSeconds() < existing.getDurationSeconds()) {
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
}
