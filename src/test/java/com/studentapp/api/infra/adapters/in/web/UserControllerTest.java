package com.studentapp.api.infra.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentapp.api.domain.model.Role;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.*;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserResponse;
import com.studentapp.api.infra.adapters.in.web.mapper.*;
import com.studentapp.api.infra.config.JwtService;
import com.studentapp.api.infra.config.exception.custom.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // Satisfy JwtAuthFilter dependencies
    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    UserDetailsService userDetailsService;

    // UserController use case dependencies
    @MockitoBean
    UserUseCase userUseCase;

    @MockitoBean
    PeriodUseCase periodUseCase;

    @MockitoBean
    NoteUseCase noteUseCase;

    @MockitoBean
    SubjectUseCase subjectUseCase;

    @MockitoBean
    PlannerEventUseCase plannerEventUseCase;

    @MockitoBean
    UserPreferenceUseCase userPreferenceUseCase;

    // UserController DTO mapper dependencies
    @MockitoBean
    UserDtoMapper userDtoMapper;

    @MockitoBean
    PeriodDtoMapper periodDtoMapper;

    @MockitoBean
    NoteDtoMapper noteDtoMapper;

    @MockitoBean
    SubjectDtoMapper subjectDtoMapper;

    @MockitoBean
    PlannerEventDtoMapper plannerEventDtoMapper;

    @MockitoBean
    UserPreferenceDtoMapper userPreferenceDtoMapper;

    private User buildUser() {
        return User.fromState(
                UUID.randomUUID(), "Test User", "test@example.com", "hashedPassword",
                null, null, 0, 1, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
    }

    @Test
    @WithMockUser
    void createUser_validRequest_returns201() throws Exception {
        User createdUser = buildUser();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(createdUser.getId());

        when(userUseCase.createUser(anyString(), anyString(), anyString(), any(Role.class))).thenReturn(createdUser);
        when(userDtoMapper.toResponse(createdUser)).thenReturn(userResponse);

        Map<String, String> body = Map.of(
                "name", "Test User",
                "email", "test@example.com",
                "password", "password123"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void createUser_blankName_returns400() throws Exception {
        Map<String, String> body = Map.of(
                "name", "",
                "email", "test@example.com",
                "password", "password123"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createUser_nameTooShort_returns400() throws Exception {
        Map<String, String> body = Map.of(
                "name", "A",
                "email", "test@example.com",
                "password", "password123"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createUser_invalidEmailFormat_returns400() throws Exception {
        Map<String, String> body = Map.of(
                "name", "Test User",
                "email", "notanemail",
                "password", "password123"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createUser_missingEmail_returns400() throws Exception {
        Map<String, String> body = Map.of(
                "name", "Test User",
                "password", "password123"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createUser_passwordTooShort_returns400() throws Exception {
        Map<String, String> body = Map.of(
                "name", "Test User",
                "email", "test@example.com",
                "password", "abc"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createUser_missingPassword_returns400() throws Exception {
        Map<String, String> body = Map.of(
                "name", "Test User",
                "email", "test@example.com"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createUser_emailAlreadyExists_returns409() throws Exception {
        when(userUseCase.createUser(anyString(), anyString(), anyString(), any(Role.class)))
                .thenThrow(new EmailAlreadyExistsException("Email already exists"));

        Map<String, String> body = Map.of(
                "name", "Test User",
                "email", "test@example.com",
                "password", "password123"
        );

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    void getUserById_found_returns200() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = buildUser();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);

        when(userUseCase.findUserById(userId)).thenReturn(Optional.of(user));
        when(userDtoMapper.toResponse(user)).thenReturn(userResponse);

        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getUserById_notFound_returns404() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userUseCase.findUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isNotFound());
    }
}
