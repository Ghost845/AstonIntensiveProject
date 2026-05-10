package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnUsers() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.id = 1L;
        user.name = "Test";
        user.email = "test@mail.com";
        user.age = 25;

        when(userService.getAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].email").value("test@mail.com"))
                .andExpect(jsonPath("$[0].age").value(25));
    }

    @Test
    void create_shouldReturnUser() throws Exception {

        UserRequestDto request = new UserRequestDto();
        request.name = "Test";
        request.email = "test@mail.com";
        request.age = 25;

        UserResponseDto response = new UserResponseDto();
        response.id = 1L;
        response.name = "Test";
        response.email = "test@mail.com";
        response.age = 25;

        when(userService.create(any(UserRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.email").value("test@mail.com"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void getById_shouldReturnUser() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.id = 1L;
        user.name = "Test";
        user.email = "test@mail.com";
        user.age = 25;

        when(userService.getById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.all-users.href").exists());
    }

    @Test
    void update_shouldReturnUpdatedUser() throws Exception {

        UserRequestDto request = new UserRequestDto();
        request.name = "Updated";
        request.email = "updated@mail.com";
        request.age = 30;

        UserResponseDto response = new UserResponseDto();
        response.id = 1L;
        response.name = "Updated";
        response.email = "updated@mail.com";
        response.age = 30;

        when(userService.update(any(Long.class), any(UserRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService).delete(1L);
    }

    @Test
    void swagger_shouldBeAvailable() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}