package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.PasswordResetToken;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.PasswordResetTokenEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PasswordResetTokenMapper {

    private final UserMapper userMapper;
    public PasswordResetTokenMapper(@Lazy UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PasswordResetTokenEntity toEntity(PasswordResetToken passwordResetToken){
        if (passwordResetToken == null) {
            return null;
        }

        UserEntity userEntity = userMapper.toEntity(passwordResetToken.getUser());

        return new PasswordResetTokenEntity(
                passwordResetToken.getId(),
                passwordResetToken.getToken(),
                passwordResetToken.getExpiresAt(),
                passwordResetToken.getUsedAt(),
                passwordResetToken.getCreatedAt(),
                userEntity
        );
    }

    public PasswordResetToken toDomain(PasswordResetTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        User userDomain = null;
        if(entity.getUser() != null){
            UserEntity userEntity = entity.getUser();

            userDomain = User.fromState(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getPasswordHash(),
                    userEntity.getCourse(),
                    userEntity.getCurrentSemester(),
                    userEntity.getCreatedAt(),
                    userEntity.getUpdatedAt(),
                    new ArrayList<>()
            );
        }

        return PasswordResetToken.fromState(entity.getId(), entity.getToken(), entity.getExpiresAt(), entity.getUsedAt(), entity.getCreatedAt(), userDomain);
    }

}
