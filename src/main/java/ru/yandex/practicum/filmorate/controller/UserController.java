package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UtilForUserController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController <User> {

    //Создаем логер
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    public HashMap<Long, User> users = new HashMap<>();

    //Создание пользователя
    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validateObj(user);
        Long id = UtilForUserController.createId(users);
        user.setId(id);
        users.put(id, user);
        log.info("Пользователь " + user.getName() + " создан.");
        return user;
    }

    //Обновление пользователя
    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            User fromTheUserBase = users.get(user.getId());
            fromTheUserBase.setEmail(user.getEmail());
            fromTheUserBase.setLogin(user.getLogin());
            fromTheUserBase.setName(user.getName());
            fromTheUserBase.setBirthday(user.getBirthday());
            log.info("Пользователь обновлен");
        } else {
            throw new ValidationException("Ошибка при обновлении пользователя");
        }
        return user;
    }

    //Получение списка всех пользователей
    @Override
    @GetMapping
    public List<User> getAllRecords() {
        List<User> list = new ArrayList<User>(users.values());
        return list;
    }

    //Валидация
    @Override
    void validateObj(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Не правильный email");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("Логин пустой или содержит пробелы");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("У пользователя пустое имя, будет использоваться логин " + user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения в будущем");
        }

    }
}
