package com.studentapp.api.domain.port.in;

public interface PasswordResetUseCase {

    void requestPasswordReset(String userEmail);
    void resetPassword(String token, String newPassword);

}
