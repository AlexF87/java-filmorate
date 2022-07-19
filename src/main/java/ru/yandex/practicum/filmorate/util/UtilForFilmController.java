package ru.yandex.practicum.filmorate.util;

import java.util.HashMap;

public class UtilForFilmController {
    static Long id = 1L;

    public static Long createId(HashMap films) {
        if (films.containsKey(id)) {
            id++;
        }
        return id;
    }
}
