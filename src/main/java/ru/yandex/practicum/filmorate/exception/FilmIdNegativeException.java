package ru.yandex.practicum.filmorate.exception;

public class FilmIdNegativeException extends RuntimeException {
    public FilmIdNegativeException(String message) {
        super(message);
    }
}
