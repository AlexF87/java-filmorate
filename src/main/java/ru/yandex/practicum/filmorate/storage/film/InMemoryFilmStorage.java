package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.UtilForFilmController;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    public HashMap<Long, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        Long id = UtilForFilmController.createId(films);
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public void delete(long id) {
        films.remove(id);
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return films.get(film.getId());
    }
}
