package com.studentapp.api.infra.adapters.in.web;

import com.studentapp.api.domain.port.in.PasswordResetUseCase;
import com.studentapp.api.infra.adapters.in.web.dto.passwordResetToken.PasswordReset;
import com.studentapp.api.infra.adapters.in.web.dto.passwordResetToken.PasswordResetRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@Tag(name = "password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetUseCase passwordResetUseCase;

    @PostMapping("/request-reset")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequest passwordResetRequest){
        passwordResetUseCase.requestPasswordReset(passwordResetRequest.getEmail());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordReset passwordReset){
        try {
            passwordResetUseCase.resetPassword(passwordReset.getToken(), passwordReset.getNewPassword());
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
