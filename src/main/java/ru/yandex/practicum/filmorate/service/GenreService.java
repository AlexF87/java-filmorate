package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.IdNegativeException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
@Service
public class GenreService {
    private final GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    //Получить список всех жанров
    public Collection<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    //Получить список всех жанров заданного фильма
    public List<Genre> getFilmGenres(Long filmId) {
        return genreDao.getFilmGenres(filmId);
    }

    //Получить жанр по id
    public Genre getGenreById(int id) {
       checkGenreId(id);
        return genreDao.getGenreById(id);
    }

    //Добавить список жанров заданного фильма
    public Collection<Genre> addFilmGenres(Long filmId, List<Genre> genres) {
        return genreDao.addFilmGenres(filmId, genres);
    }

    //Удалить жанрв у заданного фильма
    public void deleteFilmGenres(Long filmId) {
        genreDao.deleteFilmGenres(filmId);
    }

    //Проверка id
    private void checkGenreId(int id) {
        if (id < 0) {
            throw new IdNegativeException("id - must be positive and > 0");
        }
    }
}
