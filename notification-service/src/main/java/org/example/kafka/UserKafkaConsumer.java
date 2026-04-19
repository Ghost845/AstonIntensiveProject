package org.example.kafka;

import org.example.event.UserEvent;
import org.example.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaConsumer {

    private final EmailService emailService;

    public UserKafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-topic")
    public void consume(UserEvent event) {

        if ("CREATE".equals(event.operation)) {
            emailService.send(
                    event.email,
                    "Account created",
                    "Ваш аккаунт на сайте был успешно создан."
            );
        }

        if ("DELETE".equals(event.operation)) {
            emailService.send(
                    event.email,
                    "Account deleted",
                    "Ваш аккаунт был удалён."
            );
        }
    }
}