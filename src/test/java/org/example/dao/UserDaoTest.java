package org.example.dao;

import org.example.config.HibernateUtil;
import org.example.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoTest {

    private PostgreSQLContainer<?> postgres;
    private UserDao userDao;

    @BeforeAll
    void setupContainer() {
        postgres = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");

        postgres.start();

        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());

        userDao = new UserDaoImpl();
    }

    @AfterAll
    void stopContainer() {
        postgres.stop();
    }

    @BeforeEach
    void cleanDb() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (var session = sf.openSession()) {
            var tx = session.beginTransaction();
            session.createQuery("delete from UserEntity").executeUpdate();
            tx.commit();
        }
    }

    @Test
    void save_and_findById() {
        UserEntity user = new UserEntity("Test", "test@mail.com", 25);

        userDao.save(user);

        UserEntity found = userDao.findById(user.getId());

        assertNotNull(found);
        assertEquals("Test", found.getName());
    }

    @Test
    void findAll_shouldReturnUsers() {
        userDao.save(new UserEntity("A", "a@mail.com", 20));
        userDao.save(new UserEntity("B", "b@mail.com", 30));

        List<UserEntity> users = userDao.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void update_shouldModifyUser() {
        UserEntity user = new UserEntity("Old", "old@mail.com", 20);
        userDao.save(user);

        user.setName("New");
        userDao.update(user);

        UserEntity updated = userDao.findById(user.getId());

        assertEquals("New", updated.getName());
    }

    @Test
    void delete_shouldRemoveUser() {
        UserEntity user = new UserEntity("Test", "test@mail.com", 22);
        userDao.save(user);

        userDao.delete(user.getId());

        UserEntity deleted = userDao.findById(user.getId());

        assertNull(deleted);
    }
}