package com.studentapp.api.infra.config;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.domain.port.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityDueNotificationScheduler {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final NotificationUseCase notificationUseCase;
    private final NotificationRepositoryPort notificationRepositoryPort;

    @Scheduled(cron = "0 0 8 * * *")
    public void notifyActivitiesDueSoon() {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = from.plusHours(24);

        List<Activity> activities = activityRepositoryPort.findIncompleteAndDueBetween(from, to);

        for (Activity activity : activities) {
            UUID userId = activity.getSubject().getUser().getId();
            UUID activityId = activity.getId();

            if (!notificationRepositoryPort.existsUnreadByUserIdAndTypeAndReferenceId(userId, NotificationType.ACTIVITY_DUE, activityId)) {
                notificationUseCase.createNotification(new NotificationUseCase.CreateNotificationData(
                        activity.getSubject().getUser(),
                        NotificationType.ACTIVITY_DUE,
                        "A atividade \"" + activity.getTitle() + "\" vence em menos de 24 horas.",
                        activityId
                ));
            }
        }
    }
}
