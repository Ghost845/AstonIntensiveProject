package org.example.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * DTO для возврата данных пользователя через REST API.
 * <p>
 * Используется как выходная модель и не содержит внутренней логики сущности.
 */
public class UserResponseDto extends RepresentationModel<UserResponseDto> {

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
