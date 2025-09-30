package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Period;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodUseCase {

    Period createPeriod(Period period);

    Period updatePeriod(Period period);

    Optional<Period> findPeriodById(UUID id);

    Page<Period> findAllPeriods(Pageable pageable);

    Optional<List<Period>> findPeriodsByUserId(UUID id);

    void deletePeriod(UUID id);

}
