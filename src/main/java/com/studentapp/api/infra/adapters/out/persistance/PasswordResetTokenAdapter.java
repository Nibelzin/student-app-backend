package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.PasswordResetToken;
import com.studentapp.api.domain.port.out.PasswordResetTokenRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.PasswordResetTokenEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.PasswordResetTokenMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.PasswordResetTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenAdapter implements PasswordResetTokenRepositoryPort {

    private final PasswordResetTokenJpaRepository passwordResetTokenRepository;
    private final PasswordResetTokenMapper passwordResetTokenMapper;

    @Override
    public void save(PasswordResetToken token){
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenMapper.toEntity(token);
        passwordResetTokenRepository.save(passwordResetTokenEntity);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token){
        Optional<PasswordResetTokenEntity> foundPasswordResetTokenEntity = passwordResetTokenRepository.findByToken(token);
        return foundPasswordResetTokenEntity.map(passwordResetTokenMapper::toDomain);
    }

    @Override
    public void delete(PasswordResetToken token){
        passwordResetTokenRepository.delete(passwordResetTokenMapper.toEntity(token));
    }

}
