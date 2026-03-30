package com.studentapp.api.domain.service;

import com.studentapp.api.domain.enums.Role;
import com.studentapp.api.domain.model.assessment.Assessment;
import com.studentapp.api.domain.model.subject.Subject;
import com.studentapp.api.domain.model.user.User;
import com.studentapp.api.domain.port.in.AssessmentUseCase;
import com.studentapp.api.domain.port.out.AssessmentRepositoryPort;
import com.studentapp.api.domain.port.out.SubjectRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceImplTest {

    @Mock AssessmentRepositoryPort assessmentRepositoryPort;
    @Mock SubjectRepositoryPort subjectRepositoryPort;

    @InjectMocks AssessmentServiceImpl assessmentService;

    private User buildUser() {
        return User.fromState(
                UUID.randomUUID(), "Test", "test@example.com", "hash",
                null, null, 0, 1, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
    }

    private Subject buildSubject(User user) {
        return Subject.fromState(
                UUID.randomUUID(), "Math", "Prof", "Room 1", "#FFF", 10,
                user, null, LocalDateTime.now(), LocalDateTime.now()
        );
    }

    private Assessment buildAssessment(Subject subject, User user) {
        return Assessment.fromState(
                UUID.randomUUID(), "Midterm", LocalDate.of(2026, 4, 15),
                8.0, 10.0, 1.0,
                LocalDateTime.now(), LocalDateTime.now(), subject, user
        );
    }

    @Test
    void createAssessment_success() {
        User user = buildUser();
        Subject subject = buildSubject(user);
        when(subjectRepositoryPort.findById(subject.getId())).thenReturn(Optional.of(subject));
        when(assessmentRepositoryPort.save(any(Assessment.class))).thenAnswer(inv -> inv.getArgument(0));

        AssessmentUseCase.CreateAssessmentData data = new AssessmentUseCase.CreateAssessmentData(
                "Midterm", LocalDate.of(2026, 4, 15), 8.0, 10.0, 1.0, subject.getId()
        );

        Assessment result = assessmentService.createAssessment(data);

        assertThat(result.getTitle()).isEqualTo("Midterm");
        assertThat(result.getGrade()).isEqualTo(8.0);
        assertThat(result.getSubject()).isEqualTo(subject);
        assertThat(result.getUser()).isEqualTo(user);
        verify(assessmentRepositoryPort).save(any(Assessment.class));
    }

    @Test
    void createAssessment_subjectNotFound_throws() {
        UUID subjectId = UUID.randomUUID();
        when(subjectRepositoryPort.findById(subjectId)).thenReturn(Optional.empty());

        AssessmentUseCase.CreateAssessmentData data = new AssessmentUseCase.CreateAssessmentData(
                "Midterm", LocalDate.of(2026, 4, 15), 8.0, 10.0, 1.0, subjectId
        );

        assertThatThrownBy(() -> assessmentService.createAssessment(data))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateAssessment_updatesAllFields() {
        User user = buildUser();
        Subject subject = buildSubject(user);
        Assessment existing = buildAssessment(subject, user);
        when(assessmentRepositoryPort.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(assessmentRepositoryPort.save(any(Assessment.class))).thenAnswer(inv -> inv.getArgument(0));

        AssessmentUseCase.UpdateAssessmentData data = new AssessmentUseCase.UpdateAssessmentData(
                "Final", LocalDate.of(2026, 6, 20), 9.5, 10.0, 2.0
        );

        Assessment result = assessmentService.updateAssessment(existing.getId(), data);

        assertThat(result.getTitle()).isEqualTo("Final");
        assertThat(result.getAssessmentDate()).isEqualTo(LocalDate.of(2026, 6, 20));
        assertThat(result.getGrade()).isEqualTo(9.5);
        assertThat(result.getMaxGrade()).isEqualTo(10.0);
        assertThat(result.getWeight()).isEqualTo(2.0);
    }

    @Test
    void updateAssessment_partialUpdate_nullFieldsUntouched() {
        User user = buildUser();
        Subject subject = buildSubject(user);
        Assessment existing = buildAssessment(subject, user);
        when(assessmentRepositoryPort.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(assessmentRepositoryPort.save(any(Assessment.class))).thenAnswer(inv -> inv.getArgument(0));

        AssessmentUseCase.UpdateAssessmentData data = new AssessmentUseCase.UpdateAssessmentData(
                null, null, 7.0, null, null
        );

        Assessment result = assessmentService.updateAssessment(existing.getId(), data);

        assertThat(result.getTitle()).isEqualTo("Midterm");
        assertThat(result.getAssessmentDate()).isEqualTo(LocalDate.of(2026, 4, 15));
        assertThat(result.getGrade()).isEqualTo(7.0);
        assertThat(result.getMaxGrade()).isEqualTo(10.0);
        assertThat(result.getWeight()).isEqualTo(1.0);
    }

    @Test
    void updateAssessment_notFound_throws() {
        UUID id = UUID.randomUUID();
        when(assessmentRepositoryPort.findById(id)).thenReturn(Optional.empty());

        AssessmentUseCase.UpdateAssessmentData data = new AssessmentUseCase.UpdateAssessmentData(
                "Final", null, null, null, null
        );

        assertThatThrownBy(() -> assessmentService.updateAssessment(id, data))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findAssessmentById_found() {
        User user = buildUser();
        Subject subject = buildSubject(user);
        Assessment assessment = buildAssessment(subject, user);
        when(assessmentRepositoryPort.findById(assessment.getId())).thenReturn(Optional.of(assessment));

        Optional<Assessment> result = assessmentService.findAssessmentById(assessment.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(assessment.getId());
    }

    @Test
    void findAssessmentById_notFound() {
        UUID id = UUID.randomUUID();
        when(assessmentRepositoryPort.findById(id)).thenReturn(Optional.empty());

        Optional<Assessment> result = assessmentService.findAssessmentById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void findAssessments_delegatesToRepository() {
        User user = buildUser();
        Subject subject = buildSubject(user);
        Assessment assessment = buildAssessment(subject, user);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Assessment> page = new PageImpl<>(List.of(assessment));

        AssessmentUseCase.AssessmentQueryData query = new AssessmentUseCase.AssessmentQueryData(
                Optional.of(subject.getId()), Optional.empty(), Optional.empty()
        );

        when(assessmentRepositoryPort.findByQuery(query, pageable)).thenReturn(page);

        Page<Assessment> result = assessmentService.findAssessments(query, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(assessmentRepositoryPort).findByQuery(query, pageable);
    }

    @Test
    void deleteAssessment_delegatesToRepository() {
        UUID id = UUID.randomUUID();

        assessmentService.deleteAssessment(id);

        verify(assessmentRepositoryPort).delete(id);
    }
}
