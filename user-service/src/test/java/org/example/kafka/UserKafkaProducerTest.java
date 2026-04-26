package org.example.kafka;

import org.example.event.UserEvent;
import org.example.event.UserOperation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

class UserKafkaProducerTest {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
    private final UserKafkaProducer producer = new UserKafkaProducer(kafkaTemplate);

    @Test
    void send_shouldSendMessage() {
        UserEvent event = new UserEvent(UserOperation.CREATE, "test@mail.com");

        producer.send(event);

        Mockito.verify(kafkaTemplate).send(Mockito.anyString(), Mockito.eq(event));
    }
}