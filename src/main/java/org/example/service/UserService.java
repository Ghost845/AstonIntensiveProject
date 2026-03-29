package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.User;

import java.util.List;

public class UserService {

    private final UserDao userDao = new UserDaoImpl();

    public void createUser(String name, String email, int age) {
        userDao.save(new User(name, email, age));
    }

    public User getUser(Long id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(Long id, String name, String email, int age) {
        User user = userDao.findById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.update(user);
        }
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}