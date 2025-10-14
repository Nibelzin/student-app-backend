package com.studentapp.api.infra.adapters.in.web.dto.passwordResetToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    private String email;
}
