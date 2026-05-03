package org.example.service;

import com.sun.org.slf4j.internal.LoggerFactory;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.example.event.UserEvent;
import org.example.event.UserOperation;
import org.example.kafka.UserKafkaProducer;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserKafkaProducer producer;
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository, UserKafkaProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        UserEntity user = UserMapper.ofDto(dto);
        UserEntity saved = repository.save(user);

        producer.send(new UserEvent(UserOperation.CREATE, saved.getEmail()));

        return UserMapper.toDto(saved);
    }

    @Override
    public UserResponseDto getById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.name);
        user.setEmail(dto.email);
        user.setAge(dto.age);

        return UserMapper.toDto(repository.save(user));
    }

    @Override
    public void delete(Long id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.deleteById(id);

        producer.send(new UserEvent(UserOperation.DELETE, user.getEmail()));
    }

    @CircuitBreaker(name = "kafkaCircuit", fallbackMethod = "fallbackKafka")
    public void sendKafkaEvent(UserOperation operation, String email) {

        log.info("Sending Kafka event: {} {}", operation, email);

        producer.send(new UserEvent(operation, email));
    }

    public void fallbackKafka(UserOperation operation, String email, Throwable t) {
        log.error("Kafka unavailable. Fallback triggered. operation={}, email={}",
                operation, email, t);
    }
}