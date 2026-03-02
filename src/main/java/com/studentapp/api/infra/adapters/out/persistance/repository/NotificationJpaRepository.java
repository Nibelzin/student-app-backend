package com.studentapp.api.infra.adapters.out.persistance.repository;

import com.studentapp.api.infra.adapters.out.persistance.entity.NotificationEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findByUser(UserEntity user, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationEntity n SET n.isRead = true WHERE n.user.id = :userId")
    void markAllAsReadByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(n) > 0 FROM NotificationEntity n WHERE n.user.id = :userId AND n.type = :type AND n.referenceId = :referenceId AND n.isRead = false")
    boolean existsUnreadByUserIdAndTypeAndReferenceId(@Param("userId") UUID userId, @Param("type") String type, @Param("referenceId") UUID referenceId);
}
