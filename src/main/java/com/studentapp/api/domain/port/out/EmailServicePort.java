package com.studentapp.api.domain.port.out;

public interface EmailServicePort {
    void sendPasswordResetEmail(String email, String token);
}
