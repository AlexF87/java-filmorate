package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.IdNegativeException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;
    private final FriendsService friendsService;

    @Autowired
    public UserService(UserDao userDao, FriendsService friendsService) {
        this.userDao = userDao;
        this.friendsService = friendsService;
    }

    //Создание пользователя
    public User create(User user) {
        validateObj(user);
        return userDao.createUser(user);
    }

    //Удаление пользователя
    public void deleteUser(long id) {
        if (checkUserInDB(id)) {
            userDao.deleteUser(id);
        } else {
            throw new NotFoundException("The user with id= " + id + " does not exist.");
        }
    }

    //Обновление пользователя
    public User update(User user) {
        checkUserId(user.getId());
        if (checkUserInDB(user.getId())) {
             return userDao.updateUser(user);
        } else {
            throw new NotFoundException("the user with id= " + user.getId() + " does not exist.");
        }
    }

    //Получение всех пользователей
     public List<User> getAllRecords() {
         return userDao.getAll();
     }

     //Получить пользователя
    public User getUser(long id) {
        checkUserId(id);
        if (checkUserInDB(id)) {
            return userDao.getUser(id);
        } else {
            throw new NotFoundException("The user with id= " + id + " does not exist.");
        }
    }

    //Добавить в друзья
    public void addFriend(long id, long friendId) {
        if (checkUserInDB(id) && checkUserInDB(friendId)) {
            friendsService.createFriend(id, friendId);
        } else {
            throw new NotFoundException("the user with id= " + id + "or " + friendId +
                    " does not exist.");
        }
    }

    //Удалить из друзей
    public void deleteFriend(long id, long friendId) {
        checkUserId(id);
        checkUserId(friendId);
        if (checkUserInDB(id) && checkUserInDB(friendId)) {
            friendsService.deleteFriend(id, friendId);
        } else {
            throw new NotFoundException("the user with id= " + id + "or " + friendId +
                    " does not exist.");
        }
    }

    //Получить друзей пользователя
    public List<User> getFriendsOfUser(long id) {
        checkUserId(id);
        if (checkUserInDB(id)) {
            return friendsService.getFriendsOfUser(id);
        } else {
            throw new NotFoundException("the user with id= " + id + " does not exist.");
        }
    }

    //Получить общих друзей пользователей
    public List<User> getListOfSharedFriendsUsers(long id, long otherId) {
        if (checkUserInDB(id) && checkUserInDB(otherId)) {
            return friendsService.getCommonsFriend(id, otherId);
        } else {
            throw new NotFoundException("the user with id= " + id + "or " + otherId +
                    " does not exist.");
        }
    }

    //Проверка наличия пользователя в БД
    public boolean checkUserInDB(long id) {
        return userDao.checkUserInDB(id);
    }

    //Проверка id
    private void checkUserId(long id) {
        if (id < 0) {
            throw new IdNegativeException("id должно быть положительным и больше 0");
        }
    }

    //Валидация
    public void validateObj(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
