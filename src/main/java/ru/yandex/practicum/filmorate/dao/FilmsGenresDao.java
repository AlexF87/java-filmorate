package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmsGenresDao {

    //Получить все жанры заданных фильмов
    List<Film> getGenresForGivenIds(String idFilms, Map<Long, Film> films);
}
