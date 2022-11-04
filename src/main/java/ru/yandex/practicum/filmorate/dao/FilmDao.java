package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {

    //Создать фильм
    Film createFilm(Film film);

    //Получить фильм
    Film getFilm(Long id);

    //Получить все фильмы
    List<Film> getAllFilms();

    //Сохранить лайк фильму
    void saveLike(Long filmId, Long userId);

    //Обновить фильм
    void updateFilm(Film film);

    //Удалить фильм
    void deleteLike(Long filmId, Long userId);

    //Проверить лайк пользователя к фильму
    boolean checkLikeFromUser(Long filmId, Long userId);

    //Вывести список популяпных фильмов
    List<Film> getPopularFilms(int count);

    //Проверить наличие фильма в БД
    boolean checkFilmInDB(Long id);

    //Удаление фильма
    void deleteFilm(Long id);
}
