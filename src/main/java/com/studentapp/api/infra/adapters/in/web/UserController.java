package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.UserCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.UserResponse;
import com.studentapp.api.infra.adapters.in.web.dto.UserUpdateRequest;
import com.studentapp.api.infra.adapters.in.web.mapper.UserDtoMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;


    @Operation(summary = "Cria um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "O email informado já está em uso")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {

        User userToCreate = userDtoMapper.toDomain(userCreateRequest);

        User createdUser = userUseCase.createUser(userToCreate.getName(), userToCreate.getEmail(), userCreateRequest.getPassword());

        UserResponse userResponse = userDtoMapper.toResponse(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        Optional<User> founduser = userUseCase.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userDtoMapper.toResponse(founduser.get()));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAllUsers(Pageable pageable) {
        Page<User> userPage = userUseCase.findAllUsers(pageable);

        Page<UserResponse> userResponsePage = userPage.map(userDtoMapper::toResponse);

        return ResponseEntity.status(HttpStatus.OK).body(userResponsePage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
