package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    User update(User user);
    void delete(UUID id);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
