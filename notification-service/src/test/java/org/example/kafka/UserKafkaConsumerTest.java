package org.example.kafka;

import org.example.event.UserEvent;
import org.example.event.UserOperation;
import org.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserKafkaConsumerTest {

    private final EmailService emailService = Mockito.mock(EmailService.class);
    private final UserKafkaConsumer consumer = new UserKafkaConsumer(emailService);

    @Test
    void consume_create_shouldSendEmail() {
        consumer.consume(new UserEvent(UserOperation.CREATE, "test@mail.com"));

        Mockito.verify(emailService)
                .send(Mockito.eq("test@mail.com"), Mockito.any(), Mockito.any());
    }

    @Test
    void consume_delete_shouldSendEmail() {
        consumer.consume(new UserEvent(UserOperation.DELETE, "test@mail.com"));

        Mockito.verify(emailService)
                .send(Mockito.eq("test@mail.com"), Mockito.any(), Mockito.any());
    }
}