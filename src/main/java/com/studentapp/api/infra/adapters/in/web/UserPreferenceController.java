package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.UserPreference;
import com.studentapp.api.domain.port.in.UserPreferenceUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceRequest;
import com.studentapp.api.infra.adapters.in.web.dto.userPreference.UserPreferenceResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.UserPreferenceDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user-preferences")
@Tag(name = "user-preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceUseCase userPreferenceUseCase;
    private final UserPreferenceDtoMapper userPreferenceDtoMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserPreferenceResponse> getUserPreferenceById(@PathVariable UUID id){
        Optional<UserPreference> foundUserPreference = userPreferenceUseCase.findUserPreferenceById(id);

        return foundUserPreference
                .map(userPreferenceDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserPreferenceResponse> createUserPreference(@Valid @RequestBody UserPreferenceRequest userPreferenceRequest){

        UserPreference domainUserPreference = userPreferenceDtoMapper.toDomain(userPreferenceRequest);

        UserPreference createdUserPreference = userPreferenceUseCase.createUserPreference(
                domainUserPreference.getTheme(),
                domainUserPreference.getLanguage(),
                domainUserPreference.getDashboardLayout(),
                domainUserPreference.getSettings(),
                domainUserPreference.getUser().getId()
        );

        return ResponseEntity.ok(userPreferenceDtoMapper.toResponse(createdUserPreference));

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPreferenceResponse> updateUserPreference(@PathVariable UUID id, @Valid @RequestBody UserPreferenceRequest userPreferenceRequest){
        UserPreference domainUserPreference = userPreferenceDtoMapper.toDomain(userPreferenceRequest);

        UserPreferenceUseCase.UserPreferenceUpdateData updateData = new UserPreferenceUseCase.UserPreferenceUpdateData(
                domainUserPreference.getTheme(),
                domainUserPreference.getLanguage(),
                domainUserPreference.getDashboardLayout(),
                domainUserPreference.getSettings()
        );

        UserPreference updatedUserPreference = userPreferenceUseCase.updateUserPreference(id, updateData);

        return ResponseEntity.ok(userPreferenceDtoMapper.toResponse(updatedUserPreference));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPreference(@PathVariable UUID id){
        userPreferenceUseCase.deleteUserPreference(id);
        return ResponseEntity.noContent().build();
    }

}
