package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Period;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.port.in.PeriodUseCase;
import com.studentapp.api.domain.port.in.SubjectUseCase;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponse;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.PeriodDtoMapper;
import com.studentapp.api.infra.adapters.in.web.mapper.SubjectDtoMapper;
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
    private final SubjectUseCase subjectUseCase;
    private final PeriodDtoMapper periodDtoMapper;
    private final SubjectDtoMapper subjectDtoMapper;

    @GetMapping
    public ResponseEntity<Page<PeriodResponse>> getAllPeriods(Pageable pageable) {
        Page<Period> periodPage = periodUseCase.findAllPeriods(pageable);

        Page<PeriodResponse> periodResponsePage = periodPage.map(periodDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(periodResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodResponse> getPeriodById(@PathVariable UUID id) {

        Optional<Period> foundPeriod = periodUseCase.findPeriodById(id);

        return ResponseEntity.status(HttpStatus.OK).body(periodDtoMapper.toResponse(foundPeriod.get()));

    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<Page<SubjectResponse>> getSubjectsByPeriodId(@PathVariable UUID id, Pageable pageable) {
        Optional<Period> foundPeriod = periodUseCase.findPeriodById(id);

        Page<Subject> subjectPage = subjectUseCase.findSubjectsByPeriodId(foundPeriod.get().getId(), pageable);

        Page<SubjectResponse> subjectResponsePage = subjectPage.map(subjectDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(subjectResponsePage);
    }

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


    @DeleteMapping("/{id}")
    public ResponseEntity<PeriodResponse> deletePeriod(@PathVariable UUID id) {
        periodUseCase.deletePeriod(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
