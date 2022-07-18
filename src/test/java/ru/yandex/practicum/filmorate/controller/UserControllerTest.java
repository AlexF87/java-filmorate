package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void validateFailEmail() {
        UserController userController = new UserController();
        User user = new User();
        user.setEmail("");
        user.setLogin("ok");
        user.setName("qwe");
        user.setBirthday(LocalDate.of(1999, 01, 01));

        assertThrows(ValidationException.class, () -> userController.validateObj(user));
    }

    @Test
    void validateEmptyLogin() {
        UserController userController = new UserController();
        User user = new User();
        user.setEmail("asd@asd.ru");
        user.setLogin("");
        user.setName("qwe");
        user.setBirthday(LocalDate.of(1999, 01, 01));

        assertThrows(ValidationException.class, () -> userController.validateObj(user));
    }

    @Test
    void validateEmptyName() {
        UserController userController = new UserController();
        User user = new User();
        user.setEmail("asd@asd.ru");
        user.setLogin("AAA");
        user.setName("");
        user.setBirthday(LocalDate.of(1999, 01, 01));
        userController.validateObj(user);

        assertEquals("AAA", user.getName());
    }

    @Test
    void validateFutureBirthday() {
        UserController userController = new UserController();
        User user = new User();
        user.setEmail("asd@asd.ru");
        user.setLogin("sdf");
        user.setName("qwe");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> userController.validateObj(user));
    }
}