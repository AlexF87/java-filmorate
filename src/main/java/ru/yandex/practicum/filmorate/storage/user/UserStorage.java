package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    User create(User user); //Создание пользователя

    void delete(long id); // Удаление пользователя

    User update(User user); //Обновление пользователя

    List<User> getAllRecords(); //Получение всех пользователей

    User getUser(long id); //Получить пользователя по id
}
