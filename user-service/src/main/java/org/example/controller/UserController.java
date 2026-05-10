package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "Операции с пользователями")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public UserResponseDto create(@RequestBody UserRequestDto dto) {
        UserResponseDto user = service.create(dto);
        addLinks(user);
        return user;
    }

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    public List<UserResponseDto> getAll() {
        return service.getAll()
                .stream()
                .peek(this::addLinks)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public UserResponseDto getById(@PathVariable Long id) {
        UserResponseDto user = service.getById(id);
        addLinks(user);
        return user;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody UserRequestDto dto) {
        UserResponseDto user = service.update(id, dto);
        addLinks(user);
        return user;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    public Class<?> delete(@PathVariable Long id) {
        service.delete(id);
        return null;
    }

    private void addLinks(UserResponseDto user) {

        user.add(linkTo(methodOn(UserController.class)
                .getById(user.id)).withSelfRel());

        user.add(linkTo(methodOn(UserController.class)
                .getAll()).withRel("all-users"));

        user.add(linkTo(methodOn(UserController.class)
                .delete(user.id)).withRel("delete"));

        user.add(linkTo(methodOn(UserController.class)
                .update(user.id, null)).withRel("update"));
    }
}