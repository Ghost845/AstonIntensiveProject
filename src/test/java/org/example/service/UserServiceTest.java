package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldCallDaoSave() {
        userService.createUser("Test", "test@mail.com", 25);

        verify(userDao, times(1)).save(any(UserEntity.class));
    }

    @Test
    void getUser_shouldReturnUser() {
        UserEntity user = new UserEntity("Test", "test@mail.com", 25);
        when(userDao.findById(1L)).thenReturn(user);

        UserEntity result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals("Test", result.getName());
    }

    @Test
    void getAllUsers_shouldReturnList() {
        UserEntity user1 = new UserEntity("A", "a@mail.com", 20);
        UserEntity user2 = new UserEntity("B", "b@mail.com", 30);

        when(userDao.findAll()).thenReturn(List.of(user1, user2));

        List<UserEntity> users = userService.getAllUsers();

        assertEquals(2, users.size());

        assertEquals("A", users.get(0).getName());
        assertEquals("a@mail.com", users.get(0).getEmail());

        assertEquals("B", users.get(1).getName());
        assertEquals("b@mail.com", users.get(1).getEmail());
    }

    @Test
    void updateUser_shouldUpdateWhenExists() {
        UserEntity user = new UserEntity("Old", "old@mail.com", 20);

        when(userDao.findById(1L)).thenReturn(user);

        userService.updateUser(1L, "New", "new@mail.com", 30);

        verify(userDao).update(user);
        assertEquals("New", user.getName());
    }

    @Test
    void deleteUser_shouldCallDao() {
        userService.deleteUser(1L);

        verify(userDao).delete(1L);
    }
}