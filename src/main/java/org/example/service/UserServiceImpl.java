package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.entity.UserEntity;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(String name, String email, int age) {
        userDao.save(new UserEntity(name, email, age));
    }

    @Override
    public UserEntity getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void updateUser(Long id, String name, String email, int age) {
        UserEntity user = userDao.findById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.update(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}