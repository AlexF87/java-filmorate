package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    void validateNameEmpty() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("");
        film.setDescription("asd");
        film.setReleaseDate(LocalDate.of(2001, 01, 10));
        film.setDuration(10);
        assertThrows(ValidationException.class, () -> filmController.validateObj(film));
    }

    @Test
    void validateMaxLengthDescription() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("ok");
        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль." +
                " Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги," +
                "а именно 20 миллионов. о Куглов, который за время «своего отсутствия», " +
                "стал кандидатом Коломбани.");
        film.setReleaseDate(LocalDate.of(2001, 01, 10));
        film.setDuration(10);
        assertThrows(ValidationException.class, () -> filmController.validateObj(film));
    }

    @Test
    void validateFailReleaseDate() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("ok");
        film.setDescription("asd");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        film.setDuration(10);
        assertThrows(ValidationException.class, () -> filmController.validateObj(film));
    }

    @Test
    void validateDuration() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("ok");
        film.setDescription("asd");
        film.setReleaseDate(LocalDate.of(1995, 12, 27));
        film.setDuration(-10);
        assertThrows(ValidationException.class, () -> filmController.validateObj(film));
    }
}