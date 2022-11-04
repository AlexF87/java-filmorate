package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDao {

    //Получить пользователя
    User getUser(Long id);

    //Создать пользователя
    User createUser(User user);

    //Обновить пользователя
    User updateUser(User user);

    //Получить всех пользователей
    List<User> getAll();

    //Удалить всех пользователей
    void deleteUsers();

    //Удалить пользователя
    void deleteUser(long id);

    //Проверка наличия пользователя в БД
    boolean checkUserInDB (Long id);
}

