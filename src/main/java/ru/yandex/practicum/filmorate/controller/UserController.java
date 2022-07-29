package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController <User> {

    @Autowired
    UserService userService;

    //Создаем логер
    private final static Logger log = LoggerFactory.getLogger(UserController.class);


    //Создание пользователя
    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validateObj(user);
        User saved = userService.create(user);
        log.info("Пользователь " + user.getName() + " создан.");
        return saved;
    }

    //Обновление пользователя
    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Пользователь обновлен");
        return userService.update(user);
    }

    //Получение списка всех пользователей
    @Override
    @GetMapping
    public List<User> getAllRecords() {
       return userService.getAllRecords();
    }

    //Валидация
    @Override
    void validateObj(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("У пользователя пустое имя, будет использоваться логин " + user.getLogin());
        }
    }
}
