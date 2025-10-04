package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Period;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodUseCase {

    record PeriodUpdateData(String name, LocalDate startDate, LocalDate endDate){};

    Period createPeriod(String name, LocalDate startDate, LocalDate endDate, Boolean isCurrent, UUID userId);

    Period updatePeriod(UUID id, PeriodUpdateData periodUpdateData);

    Optional<Period> findPeriodById(UUID id);

    Page<Period> findAllPeriods(Pageable pageable);

    List<Period> findPeriodsByUserId(UUID id);

    void setCurrentPeriod(UUID id);

    void deletePeriod(UUID id);

}
