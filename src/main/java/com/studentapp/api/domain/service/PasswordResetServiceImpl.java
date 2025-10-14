package com.studentapp.api.domain.service;

import com.studentapp.api.domain.model.PasswordResetToken;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.in.PasswordResetUseCase;
import com.studentapp.api.domain.port.out.EmailServicePort;
import com.studentapp.api.domain.port.out.PasswordResetTokenRepositoryPort;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import com.studentapp.api.infra.config.exception.custom.TokenExpiredException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordResetTokenRepositoryPort tokenRepositoryPort;
    private final EmailServicePort emailServicePort;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetServiceImpl(UserRepositoryPort userRepositoryPort, PasswordResetTokenRepositoryPort tokenRepositoryPort, EmailServicePort emailServicePort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenRepositoryPort = tokenRepositoryPort;
        this.emailServicePort = emailServicePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void requestPasswordReset(String userEmail) {
        User user = userRepositoryPort.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        String tokenValue = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.create(tokenValue, user);

        tokenRepositoryPort.save(resetToken);

        String resetUrl = "http://localhost:8080/reset-password?token=" + tokenValue;
        emailServicePort.sendPasswordResetEmail(user.getEmail(), resetUrl);

    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepositoryPort.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido ou não encontrado."));

        if(resetToken.isExpired()) {
            tokenRepositoryPort.delete(resetToken);
            throw new TokenExpiredException("Token expirado.");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepositoryPort.save(user);

        tokenRepositoryPort.delete(resetToken);
    }

}
