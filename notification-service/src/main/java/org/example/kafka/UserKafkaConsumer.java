package org.example.kafka;

import org.example.event.UserEvent;
import org.example.event.UserOperation;
import org.example.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserKafkaConsumer.class);

    private final EmailService emailService;

    public UserKafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${app.kafka.topic:user-topic}")
    public void consume(UserEvent event) {

        log.info("Received event from Kafka. Operation: {}, Email: {}",
                event.operation, event.email);

        if (event.operation == UserOperation.CREATE) {
            emailService.send(
                    event.email,
                    "Account created",
                    "Здравствуйте! Ваш аккаунт на сайте был успешно создан."
            );
        }

        if (event.operation == UserOperation.DELETE) {
            emailService.send(
                    event.email,
                    "Account deleted",
                    "Здравствуйте! Ваш аккаунт был удалён."
            );
        }
    }
}