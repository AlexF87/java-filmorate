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
    private UserService userService;

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

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }
    //Добавление в друзья
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    //Удаление из друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
    }

    //Вывод друзей пользователя
    @GetMapping("/{id}/friends")
    public List<User> getFriendsOfUser(@PathVariable long id) {
       return userService.getFriendsOfUser(id);
    }

    //Вывод общих друзей
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getListOfSharedFriendsUsers(@PathVariable long id, @PathVariable long otherId)
    {
        return userService.getListOfSharedFriendsUsers(id, otherId);
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
