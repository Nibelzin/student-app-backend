package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.port.in.ActivityUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityRepositoryPort {
    Activity save(Activity activity);
    Optional<Activity> findById(UUID id);
    Page<Activity> findByQuery(ActivityUseCase.ActivityQueryData queryData, Pageable pageable);
    List<Activity> findIncompleteAndDueBetween(LocalDateTime from, LocalDateTime to);
    void delete(UUID id);

}
