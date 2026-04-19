package org.example.service;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;

import java.util.List;

/**
 * Сервисный слой для управления пользователями.
 * <p>
 * Содержит бизнес-логику приложения и выполняет операции
 * над пользователями через слой репозитория.
 * Работает с DTO, не раскрывая сущности {@code UserEntity} наружу.
 */
public interface UserService {

    /**
     * Создаёт нового пользователя.
     *
     * @param dto объект с входными данными пользователя
     * @return созданный пользователь в виде {@link UserResponseDto}
     */
    UserResponseDto create(UserRequestDto dto);

    /**
     * Возвращает пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя
     * @return пользователь в виде {@link UserResponseDto}
     * @throws RuntimeException если пользователь не найден
     */
    UserResponseDto getById(Long id);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей в виде DTO; может быть пустым
     */
    List<UserResponseDto> getAll();

    /**
     * Обновляет данные пользователя.
     *
     * @param id  идентификатор пользователя
     * @param dto новые данные пользователя
     * @return обновлённый пользователь в виде {@link UserResponseDto}
     * @throws RuntimeException если пользователь не найден
     */
    UserResponseDto update(Long id, UserRequestDto dto);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя
     */
    void delete(Long id);
}