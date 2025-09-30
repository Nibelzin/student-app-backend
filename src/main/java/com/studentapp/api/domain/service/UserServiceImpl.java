package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.UserUseCase;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.EmailAlreadyExistsException;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String name, String email, String password) {

        if(userRepositoryPort.findByEmail(email).isPresent()){
            throw new EmailAlreadyExistsException("O email informado já está em uso");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = User.create(name, email, hashedPassword);

        return userRepositoryPort.save(newUser);
    }

    @Override
    @Transactional
    public User updateUser(UUID id, UserUpdateData userUpdateData) {


        User existingUser = this.findUserById(id).get();

        if (userUpdateData.name() != null && !userUpdateData.name().isBlank()) {
            existingUser.setName(userUpdateData.name());
        }

        if (userUpdateData.email() != null && !userUpdateData.email().isBlank()) {
            existingUser.setEmail(userUpdateData.email());
        }

        if (userUpdateData.course() != null && !userUpdateData.course().isBlank()) {
            existingUser.setCourse(userUpdateData.course());
        }

        if (userUpdateData.currentSemester() != null) {
            existingUser.setCurrentSemester(userUpdateData.currentSemester());
        }

        return userRepositoryPort.update(existingUser);
    }

    @Override
    public void deleteUser(UUID id){
        userRepositoryPort.delete(id);
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return Optional.ofNullable(userRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")));
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable){
        return userRepositoryPort.findAll(pageable);
    }
}
