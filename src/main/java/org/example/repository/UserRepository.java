package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link UserEntity}.
 * <p>
 * Предоставляет стандартные CRUD-операции через Spring Data JPA.
 * Реализация создаётся автоматически во время выполнения.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}