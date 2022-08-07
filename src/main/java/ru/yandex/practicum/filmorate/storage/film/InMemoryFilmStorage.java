package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.UtilForFilmController;

import java.util.*;


@Component
public class InMemoryFilmStorage implements FilmStorage{

    private HashMap<Long, Film> films = new HashMap<>();
    private List<Film> sortedListForFilms = new ArrayList<>();

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
        Film oldFilm = films.get(film.getId());
        oldFilm.setName(film.getName());
        oldFilm.setDescription(film.getDescription());
        oldFilm.setReleaseDate(film.getReleaseDate());
        oldFilm.setDuration(film.getDuration());
        films.put(film.getId(), oldFilm);
        return films.get(film.getId());
    }

    @Override
    public List<Film> getAllRecords() {
        return new ArrayList<Film>(films.values());
    }

    @Override
    public Film getFilm(long id) {
        return films.get(id);
    }
}
