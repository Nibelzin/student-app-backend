package com.studentapp.api.domain.port.out;

import com.studentapp.api.domain.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepositoryPort {
    void save(PasswordResetToken token);
    Optional<PasswordResetToken> findByToken(String token);
    void delete(PasswordResetToken token);
}
