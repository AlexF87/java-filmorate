package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    Film create(Film film); //Создание фильма
    void delete(long id); // Удаление фильма
    Film update(Film film); //Обновление фильма
}
