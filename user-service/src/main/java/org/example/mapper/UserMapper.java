package org.example.mapper;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserEntity ofDto(UserRequestDto dto) {
        UserEntity user = new UserEntity();
        user.setName(dto.name);
        user.setEmail(dto.email);
        user.setAge(dto.age);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static UserResponseDto toDto(UserEntity user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.age = user.getAge();
        return dto;
    }
}
