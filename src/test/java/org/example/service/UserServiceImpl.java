package org.example.service;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация {@link UserService}, использующая {@link UserRepository}
 * для взаимодействия с базой данных.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        UserEntity user = UserMapper.toEntity(dto);
        return UserMapper.toDto(repository.save(user));
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
        repository.deleteById(id);
    }
}