package com.studentapp.api.domain.port.in;

import com.studentapp.api.domain.model.Role;
import com.studentapp.api.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserUseCase {

    record UserUpdateData(String name, String email, Integer currentSemester, String course){};

    User createUser(String name, String email, String password, Role role);

    User updateUser(UUID id, UserUpdateData userUpdateData);

    User updateUserRole(UUID id, Role role);

    void deleteUser(UUID id);

    Optional<User> findUserById(UUID id);

    Page<User> findAllUsers(Pageable pageable);
}
