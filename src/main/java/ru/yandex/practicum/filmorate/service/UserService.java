package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;

@Service
public class UserService {

    @Autowired
    InMemoryUserStorage inMemoryUserStorage;

    //Создание пользователя
    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    //Удаление пользователя
    public void delete(long id) {
        inMemoryUserStorage.delete(id);
    }

    //Обновление пользователя
    public User update(User user) {
        if (inMemoryUserStorage.users.containsKey(user.getId())) {
             return inMemoryUserStorage.update(user);
        } else {
            throw new NotFoundException("the user with id= " + user.getId() + " does not exist.");
        }
    }

    //Получение всех пользователей
     public List<User> getAllRecords() {
        return inMemoryUserStorage.getAllRecords();
     }

    //ToDO добваить новый сервис добавление в друзья...


}
