package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UtilForUserController;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    public HashMap<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        Long id = UtilForUserController.createId(users);
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public void delete(long id) {
            users.remove(id);
    }

    @Override
    public User update(User user) {
        User fromTheUserBase = users.get(user.getId());
        fromTheUserBase.setEmail(user.getEmail());
        fromTheUserBase.setLogin(user.getLogin());
        fromTheUserBase.setName(user.getName());
        fromTheUserBase.setBirthday(user.getBirthday());
        users.put(user.getId(), fromTheUserBase);
        return users.get(user.getId());
    }

    @Override
    public User getUser(long id) {
        return users.get(id);
    }

    @Override
    public List<User> getAllRecords() {
        return new ArrayList<User>(users.values());
    }
}
