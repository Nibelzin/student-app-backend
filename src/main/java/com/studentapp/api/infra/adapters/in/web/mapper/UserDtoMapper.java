package com.studentapp.api.infra.adapters.in.web.mapper;

import com.studentapp.api.domain.model.User;
import com.studentapp.api.infra.adapters.in.web.dto.UserCreateRequest;
import com.studentapp.api.infra.adapters.in.web.dto.UserResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoMapper {

    private final PeriodDtoMapper periodDtoMapper;

    public UserDtoMapper(PeriodDtoMapper periodDtoMapper) {
        this.periodDtoMapper = periodDtoMapper;
    }

    public User toDomain(UserCreateRequest dto) {
        return User.create(dto.getName(), dto.getEmail(), dto.getPassword());
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCourse(user.getCourse());
        response.setCurrentSemester(user.getCurrentSemester());
        response.setCreatedAt(user.getCreatedAt());

        if (user.getPeriods() != null) {
            response.setPeriods(
                    user.getPeriods().stream()
                            .map(periodDtoMapper::toSummaryResponse)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }

}
