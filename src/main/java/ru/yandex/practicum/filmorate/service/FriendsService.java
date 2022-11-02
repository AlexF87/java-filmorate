package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendsDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public class FriendsService {

    private final FriendsDao friendsDao;

    @Autowired
    public FriendsService(FriendsDao friendsDao) {
        this.friendsDao = friendsDao;
    }

    //Создать дружбу
    public void createFriend(Long userId, Long friendId) {
        friendsDao.createFriend(userId, friendId);
    };

    //Удалить дружбу между пользователями
    public void deleteFriend(Long userId, Long friendId) {
        friendsDao.deleteFriend(userId, friendId);
    };

    //Получить друзей пользователя
    List<User> getFriendsOfUser(Long id) {
        return friendsDao.getFriendsOfUser(id);
    };

    //Получить общих друзей пользователя
    List<User> getCommonsFriend(long userId, long friendId) {
        return friendsDao.getCommonsFriend(userId, friendId);
    };
}
