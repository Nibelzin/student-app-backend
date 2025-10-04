package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.PeriodUseCase;
import com.studentapp.api.domain.port.out.PeriodRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.mapper.PeriodMapper;
import com.studentapp.api.infra.config.exception.custom.PeriodConflictException;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodUseCase {

    private final PeriodRepositoryPort periodRepository;
    private final UserRepositoryPort userRepository;
    private final PeriodMapper periodMapper;

    @Override
    @Transactional
    public Period createPeriod(String name, LocalDate startDate, LocalDate endDate, Boolean isCurrent, UUID userId){

        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("A data de ínicio não pode ser posterior a data fim");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuário com ID " + userId + " não encontrado.")
        );

        List<Period> existingPeriods = periodRepository.findByUserId(userId);

        boolean hasConflict = existingPeriods.stream().anyMatch(existingPeriod ->
            startDate.isBefore(existingPeriod.getEndDate()) && endDate.isAfter(existingPeriod.getStartDate())
        );

        if(hasConflict){
            throw new PeriodConflictException("O período solicitado conflita com um período já existente.");
        }

        if(isCurrent){
            periodRepository.findCurrentByUserId(userId)
                    .ifPresent(oldCurrentPeriod -> {
                        oldCurrentPeriod.setCurrent(false);
                        periodRepository.save(oldCurrentPeriod);
                    });
        }

        Period newPeriod = Period.create(name, startDate, endDate, isCurrent, user);

        return periodRepository.save(newPeriod);
    }

    @Override
    public Period updatePeriod(UUID id, PeriodUpdateData periodUpdateData) {

        Period existingPeriod = this.findPeriodById(id).orElseThrow(
                () -> new ResourceNotFoundException("Período não encontrado.")
        );

        if (periodUpdateData.name() != null && !periodUpdateData.name().isBlank()) {
            existingPeriod.setName(periodUpdateData.name());
        }

        if (periodUpdateData.startDate() != null) {
            existingPeriod.setStartDate(periodUpdateData.startDate());
        }

        if (periodUpdateData.endDate() != null) {
            existingPeriod.setEndDate(periodUpdateData.endDate());
        }

        return periodRepository.save(existingPeriod);

    }

    @Override
    public Optional<Period> findPeriodById(UUID id) {
        return Optional.ofNullable(periodRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Período não encontrado.")
        ));
    }

    @Override
    public Page<Period> findAllPeriods(Pageable pageable) {
        return periodRepository.findAll(pageable);
    }

    @Override
    public List<Period> findPeriodsByUserId(UUID userId) {
        return periodRepository.findByUserId(userId);
    }

    @Override
    public void setCurrentPeriod(UUID id) {
        Period newCurrentPeriod = periodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Período não encontrado."));

        Optional<Period> oldCurrentPeriod = periodRepository.findCurrentByUserId(newCurrentPeriod.getUser().getId());

        if(oldCurrentPeriod.isPresent() && !oldCurrentPeriod.get().getId().equals(newCurrentPeriod.getId())){
            Period oldPeriod = oldCurrentPeriod.get();
            oldPeriod.setCurrent(false);
            periodRepository.save(oldPeriod);
        }

        newCurrentPeriod.setCurrent(true);
        periodRepository.save(newCurrentPeriod);
    }

    @Override
    public void deletePeriod(UUID id) {
        periodRepository.delete(id);
    }

}
