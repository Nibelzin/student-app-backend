package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.GamificationConfig;
import com.studentapp.api.domain.model.Material;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.ActivityUseCase;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.domain.port.out.MaterialRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.InvalidQueryException;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final SubjectRepositoryPort subjectRepositoryPort;
    private final MaterialRepositoryPort materialRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final NotificationUseCase notificationUseCase;

    @Override
    public Activity createActivity(CreateActivityData createActivityData){
        Subject subject = subjectRepositoryPort.findById(createActivityData.subjectId()).orElseThrow(
                () -> new ResourceNotFoundException("Matéria não encontrada.")
        );

        Activity newActivity = Activity.create(createActivityData.title(), createActivityData.description(), createActivityData.dueDate(), false, createActivityData.type(), subject, null);

        return activityRepositoryPort.save(newActivity);
    }

    @Override
    public Activity updateActivity(UUID id, UpdateActivityData updateActivityData){
        Activity existingActivity = activityRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Atividade não encontrada.")
        );

        if(updateActivityData.title() != null && !updateActivityData.title().isBlank()){
            existingActivity.setTitle(updateActivityData.title());
        }

        if(updateActivityData.description() != null && !updateActivityData.description().isBlank()){
            existingActivity.setDescription(updateActivityData.description());
        }

        if(updateActivityData.dueDate() != null){
            existingActivity.setDueDate(updateActivityData.dueDate());
        }

        boolean wasCompleted = Boolean.TRUE.equals(existingActivity.getCompleted());
        if(updateActivityData.isCompleted() != null){
            existingActivity.setCompleted(updateActivityData.isCompleted());
        }

        if (updateActivityData.type() != null && !updateActivityData.type().isBlank()) {
            existingActivity.setType(updateActivityData.type());
        }

        Activity saved = activityRepositoryPort.save(existingActivity);

        boolean nowCompleted = Boolean.TRUE.equals(saved.getCompleted());
        if (!wasCompleted && nowCompleted) {
            User user = saved.getSubject().getUser();
            boolean leveledUp = user.awardXp(GamificationConfig.XP_ACTIVITY_COMPLETION);
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
    public Optional<Activity> findActivityById(UUID id){
        return activityRepositoryPort.findById(id);
    }

    @Override
    public Page<Activity> findActivities(ActivityQueryData queryData, Pageable pageable){
        if(queryData.isCompleted().isPresent() && queryData.isOverdue().isPresent()){
            throw new InvalidQueryException("Não é possível buscas por 'atrasadas' e 'completas' simultaneamente.");
        }

        return activityRepositoryPort.findByQuery(queryData, pageable);
    }

    @Override
    public void deleteActivity(UUID id){

        List<Material> materials = materialRepositoryPort.findByActivityId(id);

        for (Material material : materials) {
            material.setActivity(null);
            materialRepositoryPort.save(material);
        }

        activityRepositoryPort.delete(id);
    }

}
