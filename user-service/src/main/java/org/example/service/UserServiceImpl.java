package org.example.service;

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

@Service
public class UserServiceImpl implements UserService {

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
}