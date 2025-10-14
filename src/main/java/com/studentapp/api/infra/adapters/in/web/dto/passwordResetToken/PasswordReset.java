package com.studentapp.api.infra.adapters.in.web.dto.passwordResetToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordReset {
    private String newPassword;
    private String token;
}
