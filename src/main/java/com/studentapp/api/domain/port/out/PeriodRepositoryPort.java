package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodRepositoryPort {
    Period save(Period period);
    Period update(Period period);
    void delete(UUID id);
    Optional<Period> findById(UUID id);
    Optional<Period> findCurrentByUserId(UUID userId);
    Page<Period> findAll(Pageable pageable);
    List<Period> findByUserId(UUID userId);
}
