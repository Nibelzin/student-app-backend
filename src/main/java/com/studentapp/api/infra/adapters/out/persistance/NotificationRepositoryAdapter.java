package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.Notification;
import com.studentapp.api.domain.model.NotificationType;
import com.studentapp.api.domain.port.out.NotificationRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.NotificationEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.NotificationMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.NotificationJpaRepository;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserJpaRepository;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = notificationMapper.toEntity(notification);
        return notificationMapper.toDomain(notificationJpaRepository.save(entity));
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return notificationJpaRepository.findById(id).map(notificationMapper::toDomain);
    }

    @Override
    public Page<Notification> findByUserId(UUID userId, Pageable pageable) {
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );
        return notificationJpaRepository.findByUser(userEntity, pageable).map(notificationMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        notificationJpaRepository.deleteById(id);
    }

    @Override
    public void markAllAsReadByUserId(UUID userId) {
        notificationJpaRepository.markAllAsReadByUserId(userId);
    }

    @Override
    public boolean existsUnreadByUserIdAndTypeAndReferenceId(UUID userId, NotificationType type, UUID referenceId) {
        return notificationJpaRepository.existsUnreadByUserIdAndTypeAndReferenceId(userId, type.name(), referenceId);
    }
}
