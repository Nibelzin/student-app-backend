package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.port.in.ActivityUseCase;
import com.studentapp.api.domain.port.in.PlannerEventUseCase;
import com.studentapp.api.domain.port.in.SubjectUseCase;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.plannerEvent.PlannerEventResponseSummary;
import com.studentapp.api.infra.adapters.in.web.mapper.PlannerEventDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/planner-event")
@Tag(name = "planner-event")
@RequiredArgsConstructor
public class PlannerEventController {

    private final PlannerEventUseCase plannerEventUseCase;
    private final PlannerEventDtoMapper plannerEventDtoMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PlannerEventResponseSummary> getPlannerEventById(@PathVariable UUID id){

        Optional<PlannerEvent> foundPlannerEvent = plannerEventUseCase.findPlannerEventById(id);

        return foundPlannerEvent
                .map(plannerEventDtoMapper::toSummaryResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<PlannerEventResponseSummary> createPlannerEvent(@RequestBody PlannerEventUseCase.CreatePlannerEventData createPlannerEventData){
        PlannerEvent createdPlannerEvent = plannerEventUseCase.createPlannerEvent(createPlannerEventData);

        PlannerEventResponseSummary plannerEventResponseSummary = plannerEventDtoMapper.toSummaryResponse(createdPlannerEvent);

        return ResponseEntity.ok(plannerEventResponseSummary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlannerEventResponseSummary> updatePlannerEvent(@PathVariable UUID id, @RequestBody  PlannerEventUseCase.UpdatePlannerEventData updatePlannerEventData){

        PlannerEvent updatedPlannerEvent = plannerEventUseCase.updatePlannerEvent(id, updatePlannerEventData);
        PlannerEventResponseSummary plannerEventResponseSummary = plannerEventDtoMapper.toSummaryResponse(updatedPlannerEvent);

        return ResponseEntity.ok(plannerEventResponseSummary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlannerEvent(@PathVariable UUID id){
        plannerEventUseCase.deletePlannerEvent(id);
        return ResponseEntity.noContent().build();
    }

}
