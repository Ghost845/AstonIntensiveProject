package org.example.service;

import org.example.entity.UserEntity;

import java.util.List;

/**
 * Сервисный слой для работы с пользователями.
 * <p>
 * Содержит бизнес-логику приложения и взаимодействует с DAO-слоем.
 * Предоставляет методы для выполнения CRUD-операций над сущностью {@link UserEntity}.
 */
public interface UserService {

    /**
     * Создаёт нового пользователя.
     *
     * @param name  имя пользователя
     * @param email email пользователя
     * @param age   возраст пользователя
     */
    void createUser(String name, String email, int age);

    /**
     * Возвращает пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя
     * @return найденный пользователь или {@code null}, если пользователь не найден
     */
    UserEntity getUser(Long id);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей; может быть пустым
     */
    List<UserEntity> getAllUsers();

    /**
     * Обновляет данные пользователя.
     *
     * @param id    идентификатор пользователя
     * @param name  новое имя
     * @param email новый email
     * @param age   новый возраст
     */
    void updateUser(Long id, String name, String email, int age);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя
     */
    void deleteUser(Long id);
}