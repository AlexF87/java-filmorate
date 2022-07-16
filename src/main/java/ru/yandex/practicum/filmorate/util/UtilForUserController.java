package ru.yandex.practicum.filmorate.util;

import java.util.HashMap;

public class UtilForUserController {
    static Long id = 1L;

    public static Long createId(HashMap users) {
        if (users.containsKey(id)) {
            id++;
        }
        return id;
    }

}
