package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.port.in.ActivityUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.activity.ActivityResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.ActivityDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/activity")
@Tag(name = "activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityUseCase activityUseCase;
    private final ActivityDtoMapper activityDtoMapper;

    @GetMapping
    public ResponseEntity<Page<ActivityResponse>> getActivities(ActivityUseCase.ActivityQueryData activityQueryData, Pageable pageable){
        Page<Activity> activityPage = activityUseCase.findActivities(activityQueryData, pageable);
        return ResponseEntity.ok(activityPage.map(activityDtoMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable UUID id) {
        return activityUseCase.findActivityById(id)
                .map(activityDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(@RequestBody ActivityUseCase.CreateActivityData createActivityData) {
        Activity newActivity = activityUseCase.createActivity(createActivityData);
        ActivityResponse response = activityDtoMapper.toResponse(newActivity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable UUID id, @RequestBody ActivityUseCase.UpdateActivityData updateActivityData) {
        Activity updatedActivity = activityUseCase.updateActivity(id, updateActivityData);
        ActivityResponse response = activityDtoMapper.toResponse(updatedActivity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable UUID id) {
        activityUseCase.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
