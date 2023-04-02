package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreDao {

    //Получить жанры фильма
    List<Genre> getFilmGenres(Long filmId);

    //Получить жанр по id
    Genre getGenreById(Integer id);

    //Получить список всех жанров
    Collection<Genre> getAllGenres();

    //Добавить фильму жанры
    Collection<Genre> addFilmGenres(Long filmId, List<Genre> genres);

    //Удалить у фильма жанр
    void deleteFilmGenres(Long filmId);

    //Проверка наличия жанра у фильма
    boolean checkGenreOfFilm(long id);

    //Создание записи жанра-фильма в таблицу
    void writeFilmGenres(Long filmId, Integer genresId);

    //Проверить genre в БД
    boolean checkGenreInDB(int id);
}
