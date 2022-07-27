package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UtilForUserController;

import java.util.HashMap;

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
        if (users.containsKey(id)) {
            users.remove(id);
        }
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }
}
