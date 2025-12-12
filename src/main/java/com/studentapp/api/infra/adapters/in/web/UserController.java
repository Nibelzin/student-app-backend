package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.*;
import com.studentapp.api.domain.port.in.*;
import com.studentapp.api.infra.adapters.in.web.dto.note.NoteResponse;
import com.studentapp.api.infra.adapters.in.web.dto.period.PeriodResponse;
import com.studentapp.api.infra.adapters.in.web.dto.plannerEvent.PlannerEventResponseSummary;
import com.studentapp.api.infra.adapters.in.web.dto.subject.SubjectResponse;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserResponse;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserResponseSummary;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final PeriodUseCase periodUseCase;
    private final NoteUseCase noteUseCase;
    private final SubjectUseCase subjectUseCase;
    private final PlannerEventUseCase plannerEventUseCase;
    private final UserPreferenceUseCase userPreferenceUseCase;
    private final UserDtoMapper userDtoMapper;
    private final PeriodDtoMapper periodDtoMapper;
    private final NoteDtoMapper noteDtoMapper;
    private final SubjectDtoMapper subjectDtoMapper;
    private final PlannerEventDtoMapper plannerEventDtoMapper;
    private final UserPreferenceDtoMapper userPreferenceDtoMapper;

    @GetMapping
    public ResponseEntity<Page<UserResponseSummary>> findAllUsers(Pageable pageable) {
        Page<User> userPage = userUseCase.findAllUsers(pageable);

        Page<UserResponseSummary> userResponsePage = userPage.map(userDtoMapper::toSummaryResponse);

        return ResponseEntity.status(HttpStatus.OK).body(userResponsePage);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal User user){
        UserResponse userResponse = userDtoMapper.toResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        Optional<User> foundUser = userUseCase.findUserById(id);

        return foundUser
                .map(userDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/periods")
    public ResponseEntity<List<PeriodResponse>> getPeriodsByUserId(@PathVariable UUID id){

        List<Period> userPeriods = periodUseCase.findPeriodsByUserId(id);

        List<PeriodResponse> userPeriodsResponse = userPeriods.stream().map(periodDtoMapper::toResponse).toList();

        return ResponseEntity.status(HttpStatus.OK).body(userPeriodsResponse);
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<Page<SubjectResponse>> getSubjectsByUserId(@PathVariable UUID id, Pageable pageable){

        Page<Subject> userSubjectsPage = subjectUseCase.findSubjectsByUserId(id, pageable);

        Page<SubjectResponse> userSubjectsResponsePage = userSubjectsPage.map(subjectDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(userSubjectsResponsePage);
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<Page<NoteResponse>> getNotesByUserId(
            @PathVariable UUID id,
            @RequestParam(required = false) String search,
            Pageable pageable){

        Page<Note> userNotesPage = noteUseCase.findNotesByUserId(id, search, pageable);

        Page<NoteResponse> userNotesResponsePage = userNotesPage.map(noteDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(userNotesResponsePage);
    }

    @GetMapping("/{id}/pinned-notes")
    public ResponseEntity<Page<NoteResponse>> getPinnedNotesByUserId(@PathVariable UUID id, Pageable pageable){

        Page<Note> userNotesPage = noteUseCase.findPinnedNotesByUserId(id, pageable);

        Page<NoteResponse> userNotesResponsePage = userNotesPage.map(noteDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(userNotesResponsePage);
    }

    @GetMapping("/{id}/planner-events")
    public ResponseEntity<Page<PlannerEventResponseSummary>> getPlannerEventsByUserId(@PathVariable UUID id, Pageable pageable){

        Page<PlannerEvent> plannerEventPage = plannerEventUseCase.findPlannerEventsByUserId(id, pageable);

        Page<PlannerEventResponseSummary> plannerEventResponsePage = plannerEventPage.map(plannerEventDtoMapper::toSummaryResponse);

        return ResponseEntity.status(HttpStatus.OK).body(plannerEventResponsePage);
    }

    @GetMapping("/{id}/preferences")
    public ResponseEntity<UserPreferenceResponse> getUserPreferenceByUserId(@PathVariable UUID id){
        UserPreference foundUserPreference = userPreferenceUseCase.findUserPreferenceByUserId(id);

        UserPreferenceResponse userPreferenceResponse = userPreferenceDtoMapper.toResponse(foundUserPreference);

        return ResponseEntity.status(HttpStatus.OK).body(userPreferenceResponse);
    }

    @Operation(summary = "Cria um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "O email informado já está em uso")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {

        User createdUser = userUseCase.createUser(userCreateRequest.getName(), userCreateRequest.getEmail(), userCreateRequest.getPassword());

        userPreferenceUseCase.createUserPreference(
                null,
                null,
                null,
                null,
                createdUser.getId()
        );

        UserResponse userResponse = userDtoMapper.toResponse(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Operation(summary = "Atualiza um usuário existente", method = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,@Valid @RequestBody UserUpdateRequest userUpdateRequest) {

        UserUseCase.UserUpdateData updateData = new UserUseCase.UserUpdateData(
                userUpdateRequest.getName(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getCurrentSemester(),
                userUpdateRequest.getCourse()
        );

        User updatedUser = userUseCase.updateUser(id, updateData);

        UserResponse userResponse = userDtoMapper.toResponse(updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
