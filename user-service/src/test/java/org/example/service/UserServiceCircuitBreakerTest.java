package org.example.service;

import org.example.event.UserOperation;
import org.example.kafka.UserKafkaProducer;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceCircuitBreakerTest {

    private final UserRepository repository = Mockito.mock(UserRepository.class);
    private final UserKafkaProducer producer = Mockito.mock(UserKafkaProducer.class);

    private final UserServiceImpl service =
            new UserServiceImpl(repository, producer);

    @Test
    void fallback_shouldBeCalled_whenKafkaFails() {

        Mockito.doThrow(new RuntimeException("Kafka down"))
                .when(producer)
                .send(Mockito.any());

        service.sendKafkaEvent(UserOperation.CREATE, "test@mail.com");
    }
}