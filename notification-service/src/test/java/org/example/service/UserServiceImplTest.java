package org.example.service;

import org.example.dto.UserRequestDto;
import org.example.entity.UserEntity;
import org.example.kafka.UserKafkaProducer;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserServiceImplTest {

    private final UserRepository repository = Mockito.mock(UserRepository.class);
    private final UserKafkaProducer producer = Mockito.mock(UserKafkaProducer.class);

    private final UserServiceImpl service = new UserServiceImpl(repository, producer);

    @Test
    void create_shouldSendKafkaEvent() {
        UserRequestDto dto = new UserRequestDto();
        dto.name = "Test";
        dto.email = "test@mail.com";
        dto.age = 25;

        UserEntity entity = new UserEntity();
        entity.setEmail("test@mail.com");

        Mockito.when(repository.save(Mockito.any())).thenReturn(entity);

        service.create(dto);

        Mockito.verify(producer).send(Mockito.any());
    }
}