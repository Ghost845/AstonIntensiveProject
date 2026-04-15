package org.example.dto;

/**
 * DTO для передачи данных пользователя при создании и обновлении.
 * <p>
 * Используется как входная модель в REST API.
 */
public class UserRequestDto {

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
