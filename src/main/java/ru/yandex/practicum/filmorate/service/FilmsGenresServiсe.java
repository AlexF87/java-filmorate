package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.FilmsGenresDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;

@Service
public class FilmsGenresServiсe {

    private final FilmsGenresDaoImpl filmsGenresDao;

    public FilmsGenresServiсe(FilmsGenresDaoImpl filmsGenresDao) {
        this.filmsGenresDao = filmsGenresDao;
    }
    //Получить все жанры заданных фильмов
    public List<Film> getGenresForGivenIds(String idFilms, Map<Long, Film> films) {
        return filmsGenresDao.getGenresForGivenIds(idFilms, films);
    }
}
