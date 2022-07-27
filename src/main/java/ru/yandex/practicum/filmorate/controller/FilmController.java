package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.UtilForFilmController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
@RequestMapping("/films")
public class FilmController extends  AbstractController <Film> {

    //Создаем логер
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);


    //Добавление фильма
    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validateObj(film);


        log.info("Фильм " + film.getName() + " создан.");
        return film;
    }

    //Обновление фильма
    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            Film oldFilm = films.get(film.getId());
            oldFilm.setName(film.getName());
            oldFilm.setDescription(film.getDescription());
            oldFilm.setReleaseDate(film.getReleaseDate());
            oldFilm.setDuration(film.getDuration());
            log.info("Фильм обновлен");
        } else {
            throw new ValidationException("Ошибка при обновлении фильма");
        }
        return film;
    }

    //Получение всех данных
    @Override
    @GetMapping
    public List<Film> getAllRecords() {
        List<Film> list = new ArrayList<Film>(films.values());
        return list;
    }

    //Валидация
    @Override
    void validateObj(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.info("Фильм выходит за границы даты релиза");
            throw new ValidationException("Дата релиза раньше 28.12.1895");
        }
    };

}
