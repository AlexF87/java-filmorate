package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    InMemoryFilmStorage inMemoryFilmStorage;

    //Создание фильма
    public Film create (Film film) {
        return inMemoryFilmStorage.create(film);
    }

    //Удаление фильма
    public void delete (long  id) {
        inMemoryFilmStorage.delete(id);
    }

    //Обновление фильма
    public Film update (Film film) {
        if (inMemoryFilmStorage.films.containsKey(film.getId())) {
            return inMemoryFilmStorage.update(film);
        } else {
            throw new NotFoundException("the film with id= " + film.getId() + " does not exist.");
        }
    }

    public List<Film> getAllRecords() {
        return inMemoryFilmStorage.getAllRecords();
    }

}
