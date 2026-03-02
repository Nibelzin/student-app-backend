package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.port.in.NotificationUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.notification.NotificationResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.NotificationDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@Tag(name = "notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationUseCase notificationUseCase;
    private final NotificationDtoMapper notificationDtoMapper;

    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getNotifications(
            @RequestParam UUID userId,
            Pageable pageable) {
        Page<Notification> page = notificationUseCase.findByUserId(userId, pageable);
        return ResponseEntity.ok(page.map(notificationDtoMapper::toResponse));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable UUID id) {
        Notification notification = notificationUseCase.markAsRead(id);
        return ResponseEntity.ok(notificationDtoMapper.toResponse(notification));
    }

    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@RequestParam UUID userId) {
        notificationUseCase.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        notificationUseCase.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
