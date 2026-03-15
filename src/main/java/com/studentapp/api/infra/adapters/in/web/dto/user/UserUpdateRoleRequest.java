package com.studentapp.api.infra.adapters.in.web.dto.user;

import com.studentapp.api.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRoleRequest {
    Role role;
}
