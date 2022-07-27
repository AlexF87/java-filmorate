package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
    User create(User user); //Создание пользователя
    void delete(long id); // Удаление пользователя
    User update(User user); //Обновление пользователя
}
