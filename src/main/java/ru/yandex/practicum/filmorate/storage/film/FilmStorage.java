package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film create(Film film); //Создание фильма

    void delete(long id); // Удаление фильма

    Film update(Film film); //Обновление фильма

    List<Film> getAllRecords(); //Получение всех записей

    Film getFilm(long id); //Получить фильм
}
