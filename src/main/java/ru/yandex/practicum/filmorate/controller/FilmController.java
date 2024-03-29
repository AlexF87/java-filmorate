package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController extends  AbstractController <Film> {

    private final FilmService filmService;

    //Создаем логер
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);


    //Добавление фильма
    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Фильм " + film.getName() + " создан.");
        return filmService.create(film);
    }

    //Обновление фильма
    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Обновление фильма id= " + film.getId());
        return filmService.update(film);
    }

    //Получение всех данных
    @Override
    @GetMapping
    public List<Film> getAllRecords() {
        log.info("Получение всех фильмов");
        return filmService.getAllRecords();
    }

    //Получение фильма
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        log.info("Получаем фильм id= " + id);
        return filmService.getFilm(id);
    }

    //Пользователь ставит лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Пользователь id " + userId + " ставит лайк, фильм_id= " + id);
        filmService.addLike(id, userId);
    }

    //Пользователь удаляет лайк
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Пользователь id " + userId + " удаляет лайк, фильм_id= " + id);
        filmService.deleteLike(id, userId);
    }

    //Возвращает список из первых count фильмов
    @GetMapping("/popular")
    public List<Film> getListPopularFilms(@RequestParam(required = false, defaultValue = "10")
                                              int count) {
        log.info("Возвращаем список популярных фльмов count = " + count);
        return filmService.getListPopularFilms(count);
    }
}
