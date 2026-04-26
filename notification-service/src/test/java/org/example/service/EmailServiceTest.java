package org.example.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

class EmailServiceTest {

    private final JavaMailSender mailSender = Mockito.mock(JavaMailSender.class);
    private final EmailService service = new EmailService(mailSender);

    @Test
    void send_shouldSendEmail() {
        service.send("test@mail.com", "Test", "Hello");

        Mockito.verify(mailSender).send((MimeMessage) Mockito.any());
    }
}