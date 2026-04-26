package org.example.dto;

/**
 * DTO для возврата данных пользователя через REST API.
 * <p>
 * Используется как выходная модель и не содержит внутренней логики сущности.
 */
public class UserResponseDto {

    /**
     * Уникальный идентификатор пользователя
     */
    public Long id;

    /**
     * Имя пользователя
     */
    public String name;

    /**
     * Email пользователя
     */
    public String email;

    /**
     * Возраст пользователя
     */
    public int age;
}
