package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Activity;
import com.studentapp.api.domain.model.PlannerEvent;
import com.studentapp.api.domain.model.Subject;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.PlannerEventUseCase;
import com.studentapp.api.domain.port.out.ActivityRepositoryPort;
import com.studentapp.api.domain.port.out.PlannerEventRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlannerEventServiceImpl implements PlannerEventUseCase {

    private final PlannerEventRepositoryPort plannerEventRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final SubjectRepositoryPort subjectRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public PlannerEvent createPlannerEvent(CreatePlannerEventData createPlannerEventData) {
        User user = userRepositoryPort.findById(createPlannerEventData.userId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado.")
        );

        if(createPlannerEventData.startAt().isAfter(createPlannerEventData.endAt())){
            throw new IllegalArgumentException("A data de ínicio não pode ser posterior a data fim");
        }

        Activity activity = null;
        Subject subject = null;

        if (createPlannerEventData.subjectId() != null){
            subject = subjectRepositoryPort.findById(createPlannerEventData.subjectId()).orElseThrow(
                    () -> new ResourceNotFoundException("Matéria não encontrada.")
            );
        }
        if (createPlannerEventData.activityId() != null){
            activity = activityRepositoryPort.findById(createPlannerEventData.activityId()).orElseThrow(
                    () -> new ResourceNotFoundException("Atividade não encontrada.")
            );
        }

        PlannerEvent newPlannerEvent = PlannerEvent.create(createPlannerEventData.title(),createPlannerEventData.startAt(),createPlannerEventData.endAt(),createPlannerEventData.allDay(),createPlannerEventData.rule(),createPlannerEventData.color(),user,subject,activity);

        return plannerEventRepositoryPort.save(newPlannerEvent);
    }
    
    @Override
    public PlannerEvent updatePlannerEvent(UUID id, UpdatePlannerEventData updatePlannerEventData){
        PlannerEvent existingPlannerEvent = plannerEventRepositoryPort.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Planner Event não encontrado.")
        );

        LocalDateTime newStartAt = updatePlannerEventData.startAt().orElse(existingPlannerEvent.getStartAt());
        LocalDateTime newEndAt = updatePlannerEventData.endAt().orElse(existingPlannerEvent.getEndAt());

        if (newStartAt.isAfter(newEndAt)){
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }
        
        Subject subject = null;
        Activity activity = null;

        if (updatePlannerEventData.title().isPresent()) {
            existingPlannerEvent.setTitle(updatePlannerEventData.title().get());
        }

        if (updatePlannerEventData.startAt().isPresent()) {
            existingPlannerEvent.setStartAt(updatePlannerEventData.startAt().get());
        }
        
        if (updatePlannerEventData.endAt().isPresent()) {
            existingPlannerEvent.setEndAt(updatePlannerEventData.endAt().get());
        }
        
        if (updatePlannerEventData.allDay().isPresent()) {
            existingPlannerEvent.setAllDay(updatePlannerEventData.allDay().get());
        }
        
        if (updatePlannerEventData.rule().isPresent()) {
            existingPlannerEvent.setRule(updatePlannerEventData.rule().get());
        }
        
        if (updatePlannerEventData.color().isPresent()) {
            existingPlannerEvent.setColor(updatePlannerEventData.color().get());
        }

        if (updatePlannerEventData.subjectId().isPresent()) {
            subject = subjectRepositoryPort.findById(updatePlannerEventData.subjectId().get()).orElseThrow(
                    () -> new ResourceNotFoundException("Matéria não encontrada.")
            );
            existingPlannerEvent.setSubject(subject);
        }

        if (updatePlannerEventData.activityId().isPresent()) {
            activity = activityRepositoryPort.findById(updatePlannerEventData.activityId().get()).orElseThrow(
                    () -> new ResourceNotFoundException("Atividade não encontrada.")
            );
            existingPlannerEvent.setActivity(activity);
        }

        return plannerEventRepositoryPort.save(existingPlannerEvent);
    }

    @Override
    public Optional<PlannerEvent> findPlannerEventById(UUID id){
        return plannerEventRepositoryPort.findById(id);
    }

    @Override
    public Page<PlannerEvent> findPlannerEventsByUserId(UUID userId, Pageable pageable){
        if (userRepositoryPort.findById(userId).isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }

        return plannerEventRepositoryPort.findByUserId(userId, pageable);
    }

    @Override
    public void deletePlannerEvent(UUID id){
        plannerEventRepositoryPort.delete(id);
    }
}