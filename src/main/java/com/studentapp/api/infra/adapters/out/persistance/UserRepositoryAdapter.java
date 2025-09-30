package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.domain.port.out.UserRepositoryPort;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import com.studentapp.api.infra.adapters.out.persistance.mapper.UserMapper;
import com.studentapp.api.infra.adapters.out.persistance.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user){

        UserEntity userEntity = userMapper.toEntity(user);

        UserEntity savedUserEntity = userJpaRepository.save(userEntity);

        return userMapper.toDomain(savedUserEntity);

    }

    @Override
    public User update(User user){


        UserEntity userEntity = userMapper.toEntity(user);

        UserEntity updatedUserEntity = userJpaRepository.save(userEntity);

        return  userMapper.toDomain(updatedUserEntity);
    }

    @Override
    public void delete(UUID id){
        userJpaRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(UUID id){
        Optional<UserEntity> optionalUserEntity = userJpaRepository.findById(id);

        return optionalUserEntity.map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email){
        Optional<UserEntity> optionalUserEntity = userJpaRepository.findByEmail(email);

        return optionalUserEntity.map(userMapper::toDomain);
    }

    @Override
    public Page<User> findAll(Pageable pageable){
        Page<UserEntity> userEntityPage = userJpaRepository.findAll(pageable);

        return userEntityPage.map(userMapper::toDomain);
    }
}
