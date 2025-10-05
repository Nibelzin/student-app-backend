package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.NoteEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NoteMapper {

    private final UserMapper userMapper;

    public NoteMapper(@Lazy UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public NoteEntity toEntity(Note note){
        if(note == null){
            return null;
        }

        UserEntity userEntity = userMapper.toEntity(note.getUser());

        return new NoteEntity(
                note.getId(),
                note.getContent(),
                note.isPinned(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                userEntity
        );
    }

    public Note toDomain(NoteEntity entity){
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

        return Note.fromState(entity.getId(), entity.getContent(), entity.getIsPinned(), userDomain, entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
