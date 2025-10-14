package com.studentapp.api.infra.adapters.out.persistance;

import com.studentapp.api.domain.port.out.EmailServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceAdapter implements EmailServicePort {

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(String to, String resetUrl){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("teste@teste.com");
        message.setTo(to);
        message.setSubject("Redifinição de senha");
        message.setText("Para resetar sua senha, clique no link abaixo:\n" + resetUrl);
        mailSender.send(message);
    }

}
