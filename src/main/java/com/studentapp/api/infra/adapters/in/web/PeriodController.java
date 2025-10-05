package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.port.in.PeriodUseCase;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponse;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.PeriodDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/periods", produces = {"application/json"})
@Tag(name = "period")
@RequiredArgsConstructor
public class PeriodController {

    private final PeriodUseCase periodUseCase;
    private final UserUseCase userUseCase;
    private final PeriodDtoMapper periodDtoMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PeriodResponse> createPeriod(@Valid @RequestBody PeriodCreateRequest periodCreateRequest) {

        Period createdPeriod = periodUseCase.createPeriod(periodCreateRequest.getName(), periodCreateRequest.getStartDate(), periodCreateRequest.getEndDate(), periodCreateRequest.getIsCurrent(), periodCreateRequest.getUserId());

        PeriodResponse periodResponse = periodDtoMapper.toResponse(createdPeriod);

        return new ResponseEntity<>(periodResponse, HttpStatus.CREATED);

    }

    @PostMapping("/{id}/set-current")
    public ResponseEntity<PeriodResponse> setCurrentPeriod(@PathVariable UUID id) {

        periodUseCase.setCurrentPeriod(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodResponse> updatePeriod(@PathVariable UUID id, @Valid @RequestBody PeriodUpdateRequest periodUpdateRequest) {

        PeriodUseCase.PeriodUpdateData updateData = new PeriodUseCase.PeriodUpdateData(
                periodUpdateRequest.getName(),
                periodUpdateRequest.getStartDate(),
                periodUpdateRequest.getEndDate()
        );

        Period updatedPeriod = periodUseCase.updatePeriod(id, updateData);

        PeriodResponse periodResponse = periodDtoMapper.toResponse(updatedPeriod);

        return new ResponseEntity<>(periodResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodResponse> getPeriodById(@PathVariable UUID id) {

        Optional<Period> foundPeriod = periodUseCase.findPeriodById(id);

        return ResponseEntity.status(HttpStatus.OK).body(periodDtoMapper.toResponse(foundPeriod.get()));

    }

    @GetMapping
    public ResponseEntity<Page<PeriodResponse>> getAllPeriods(Pageable pageable) {
        Page<Period> periodPage = periodUseCase.findAllPeriods(pageable);

        Page<PeriodResponse> periodResponsePage = periodPage.map(periodDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(periodResponsePage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PeriodResponse> deletePeriod(@PathVariable UUID id) {
        periodUseCase.deletePeriod(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
