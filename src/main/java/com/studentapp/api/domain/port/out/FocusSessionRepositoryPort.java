package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.FocusSession;
import com.studentapp.api.domain.port.in.FocusSessionUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FocusSessionRepositoryPort {
    FocusSession save(FocusSession session);
    Optional<FocusSession> findById(UUID id);
    Page<FocusSession> findByQuery(FocusSessionUseCase.FocusSessionQueryData query, Pageable pageable);
    void delete(UUID id);
}
