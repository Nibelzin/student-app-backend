package com.studentapp.api.infra.adapters.out.persistance.mapper;

import com.studentapp.api.domain.model.Note;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.out.persistance.entity.NoteEntity;
import com.studentapp.api.infra.adapters.out.persistance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class NoteMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    @Mapping(target = "isPinned", source = "pinned")
    @Mapping(target = "user", expression = "java(userMapper.toEntity(note.getUser()))")
    public abstract NoteEntity toEntity(Note note);

    public Note toDomain(NoteEntity entity) {
        if (entity == null) return null;

        User userDomain = null;
        if (entity.getUser() != null) {
            UserEntity u = entity.getUser();
            userDomain = User.fromState(
                    u.getId(), u.getName(), u.getEmail(), u.getPasswordHash(),
                    u.getCourse(), u.getCurrentSemester(), u.getCurrentXp(),
                    u.getCurrentLevel(), u.getCoins(), u.getCurrentStreak(),
                    u.getLastActiveDate(), u.getCreatedAt(), u.getUpdatedAt(),
                    new ArrayList<>(), u.getRole()
            );
        }

        return Note.fromState(
                entity.getId(), entity.getContent(), entity.getIsPinned(),
                userDomain, entity.getCreatedAt(), entity.getUpdatedAt()
        );
    }
}
