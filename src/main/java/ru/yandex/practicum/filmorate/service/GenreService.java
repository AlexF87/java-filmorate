package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.IdNegativeException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

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
        if (!genreDao.checkGenreInDB(id)) {
            throw new NotFoundException("Not found genre");
        }
        Genre genre = genreDao.getGenreById(id);
        return genre;
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

    //Добавление жанра к фильму
    public Map<Long, List<Genre>> addGenreToFilm(List<Long> idFilm) {
        Map<Long, List<Genre>> genres = new HashMap<>();

        for (long id : idFilm) {
            if (checkGenresOfFilm(id)) {
                genres.put(id, getFilmGenres(id));
            } else {
                genres.put(id, new ArrayList<Genre>());
            }
        }
        return genres;
    }

    //Проверка наличия жанра у фильма
    private boolean checkGenresOfFilm(long id) {
        return genreDao.checkGenreOfFilm(id);
    }

    //Запись жанра-фильма в таблицу
    public void writeFilmGenres(Long filmId, List<Genre> genres) {
        deleteFilmGenres(filmId);
        if (genres != null && genres.size() > 0) {
            for (int i = 0; i < genres.size(); i++)
                genreDao.writeFilmGenres(filmId, genres.get(i).getId());
        }
    }
}
