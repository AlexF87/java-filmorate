package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsDao {

    //Создать дружбу
    void createFriend(Long userId, Long friendId);

    //Удалить дружбу между пользователями
    void deleteFriend(Long userId, Long friendId);

    //Получить друзей пользователя
    List<User> getFriendsOfUser(Long id);

    //Получить общих друзей пользователя
    List<User> getCommonsFriend(long userId, long friendId);
}
