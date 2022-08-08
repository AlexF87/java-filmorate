package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController extends AbstractController <User> {

    private final UserService userService;

    //Создаем логер
    private final static Logger log = LoggerFactory.getLogger(UserController.class);


    //Создание пользователя
    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) {
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
        log.info("Получаем список всех пользователей");
        return userService.getAllRecords();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info("Получаем пользователя id= " + id);
        return userService.getUser(id);
    }
    //Добавление в друзья
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("У пользователя id" + id+ " и id= " + id + "дружба");
        userService.addFriend(id, friendId);
    }

    //Удаление из друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(String.format("удаление дружбы у пользователя id= %d, друг %d", id, friendId));
        userService.deleteFriend(id, friendId);
    }

    //Вывод друзей пользователя
    @GetMapping("/{id}/friends")
    public List<User> getFriendsOfUser(@PathVariable long id) {
        log.info("Вывополнен вывод друзей пользователя id= " + id);
        return userService.getFriendsOfUser(id);
    }

    //Вывод общих друзей
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getListOfSharedFriendsUsers(@PathVariable long id, @PathVariable long otherId)
    {
        log.info("Выполнен вывод общих друзей");
        return userService.getListOfSharedFriendsUsers(id, otherId);
    }
}
