package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.FocusSessionUseCase;
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

    private int calculateXp(int durationSeconds, boolean isCompleted) {
        return isCompleted ? Math.max(1, durationSeconds / 60) : 0;
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

        return focusSessionRepositoryPort.save(newSession);
    }

    @Override
    public FocusSession updateFocusSession(UUID id, UpdateFocusSessionData data) {
        FocusSession existing = focusSessionRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sessão de foco não encontrada.")
        );

        if (data.durationSeconds() != null) {
            if (data.durationSeconds() < existing.getDurationSeconds()) {
                throw new IllegalArgumentException("Não é possível diminuir a duração de uma sessão de foco.");
            }
            existing.setDurationSeconds(data.durationSeconds());
        }

        if (data.isCompleted() != null) {
            existing.setCompleted(data.isCompleted());
        }

        existing.setXpEarned(calculateXp(existing.getDurationSeconds(), existing.isCompleted()));

        return focusSessionRepositoryPort.save(existing);
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
