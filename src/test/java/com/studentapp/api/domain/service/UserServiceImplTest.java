package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.Role;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.EmailAlreadyExistsException;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepositoryPort userRepositoryPort;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    private User buildUser(UUID id) {
        return User.fromState(
                id, "Test User", "test@example.com", "hashedPassword",
                null, null, 0, 1, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
    }

    @Test
    void createUser_success() {
        User savedUser = buildUser(UUID.randomUUID());
        when(userRepositoryPort.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepositoryPort.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser("Test User", "test@example.com", "rawPassword", Role.USER);

        assertThat(result).isEqualTo(savedUser);
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void createUser_emailAlreadyExists_throwsException() {
        User existingUser = buildUser(UUID.randomUUID());
        when(userRepositoryPort.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userService.createUser("Test User", "test@example.com", "rawPassword", Role.USER))
                .isInstanceOf(EmailAlreadyExistsException.class);
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    void createUser_encodesPasswordBeforeSaving() {
        when(userRepositoryPort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepositoryPort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.createUser("Test User", "test@example.com", "rawPassword", Role.USER);

        verify(passwordEncoder).encode("rawPassword");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryPort).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("encodedPassword");
        assertThat(captor.getValue().getPassword()).isNotEqualTo("rawPassword");
    }

    @Test
    void updateUser_updatesOnlyProvidedFields() {
        UUID id = UUID.randomUUID();
        User user = User.fromState(
                id, "Old Name", "test@example.com", "hash",
                "Engineering", null, 0, 1, 0, 0, null,
                LocalDateTime.now(), LocalDateTime.now(), List.of(), Role.USER
        );
        when(userRepositoryPort.findById(id)).thenReturn(Optional.of(user));
        when(userRepositoryPort.update(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserUseCase.UserUpdateData updateData = new UserUseCase.UserUpdateData("New Name", null, null, null);
        User result = userService.updateUser(id, updateData);

        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getCourse()).isEqualTo("Engineering");
    }

    @Test
    void updateUser_userNotFound_throwsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(userRepositoryPort.findById(id)).thenReturn(Optional.empty());

        UserUseCase.UserUpdateData updateData = new UserUseCase.UserUpdateData("New Name", null, null, null);

        assertThatThrownBy(() -> userService.updateUser(id, updateData))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findUserById_exists_returnsOptional() {
        UUID id = UUID.randomUUID();
        User user = buildUser(id);
        when(userRepositoryPort.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(id);

        assertThat(result).isPresent().contains(user);
    }

    @Test
    void findUserById_notFound_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(userRepositoryPort.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteUser_delegatesToRepository() {
        UUID id = UUID.randomUUID();

        userService.deleteUser(id);

        verify(userRepositoryPort, times(1)).delete(id);
    }
}
