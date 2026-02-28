package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.Role;
import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserResponse;
import com.studentapp.api.infra.adapters.in.web.dto.user.UserResponseSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PeriodDtoMapper.class})
public abstract class UserDtoMapper {

    public User toDomain(UserCreateRequest dto) {
        return User.create(dto.getName(), dto.getEmail(), dto.getPassword(), Role.USER);
    }

    @Mapping(target = "periods", source = "periods")
    public abstract UserResponse toResponse(User user);

    public abstract UserResponseSummary toSummaryResponse(User user);
}