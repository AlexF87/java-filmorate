package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class UserService {
    private final UserStorage inMemoryUserStorage;
    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    //Создание пользователя
    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    //Удаление пользователя
    public void delete(long id) {
        if (checkUserInStorage(id)) {
            inMemoryUserStorage.delete(id);
        } else {
            throw new NotFoundException("The user with id= " + id + " does not exist.");
        }
    }

    public boolean checkUserInStorage(long id) {
        final List<User> allRecords = inMemoryUserStorage.getAllRecords();
        if (allRecords.stream().anyMatch(s->s.getId() == id)) {
            return true;
        } else {
            return false;
        }
    }
    //Обновление пользователя
    public User update(User user) {
        if (checkUserInStorage(user.getId())) {
             return inMemoryUserStorage.update(user);
        } else {
            throw new NotFoundException("the user with id= " + user.getId() + " does not exist.");
        }
    }

    //Получение всех пользователей
     public List<User> getAllRecords() {
         return inMemoryUserStorage.getAllRecords();
     }

     //Получить пользователя
    public User getUser(long id) {
        if (checkUserInStorage(id)) {
            return inMemoryUserStorage.getUser(id);
        } else {
            throw new NotFoundException("The user with id= " + id + " does not exist.");
        }
    }

    public void addFriend(long id, long friendId) {
        User user, friend;
        if (checkUserInStorage(id) && checkUserInStorage(friendId)) {
            user = inMemoryUserStorage.getUser(id);
            friend = inMemoryUserStorage.getUser(friendId);
        } else {
            throw new NotFoundException("the user with id= " + id + "or " + friendId +
                    " does not exist.");
        }
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }

    public void deleteFriend(long id, long friendId) {
        User user, friend;
        if (checkUserInStorage(id) && checkUserInStorage(friendId)) {
            user = inMemoryUserStorage.getUser(id);
            friend = inMemoryUserStorage.getUser(friendId);
        } else {
            throw new NotFoundException("the user with id= " + id + "or " + friendId +
                    " does not exist.");
        }
        Set<Long> userSet = new HashSet<>();
        userSet.addAll(user.getFriends());
        userSet.remove(friend.getId());
        user.setFriends(userSet);
        Set<Long> friendSet = new HashSet<>();
        friendSet.addAll(friend.getFriends());
        friendSet.remove(user.getId());
        friend.setFriends(friendSet);
    }

    public List<User> getFriendsOfUser(long id) {
        if (checkUserInStorage(id)) {
            User user = inMemoryUserStorage.getUser(id);
            List<User> list = new ArrayList<>();
            list = user.getFriends().stream()
                    .map(s->inMemoryUserStorage.getUser(s))
                    .collect(Collectors.toList());
            return list;
        } else {
            throw new NotFoundException("the user with id= " + id + " does not exist.");
        }
    }

    public List<User> getListOfSharedFriendsUsers(long id, long otherId) {
        if (checkUserInStorage(id) && checkUserInStorage(otherId)) {
            User user = inMemoryUserStorage.getUser(id);
            User otherUser = inMemoryUserStorage.getUser(otherId);
            List<User> list = new ArrayList<>();
            list = user.getFriends().stream()
                    .filter(s->otherUser.getFriends().contains(s) && s != otherUser.getId())
                    .map(s->inMemoryUserStorage.getUser(s))
                    .collect(Collectors.toList());
            return list;
        } else {
            throw new NotFoundException("the user with id= " + id + "or " + otherId +
                    " does not exist.");
        }
    }
}
