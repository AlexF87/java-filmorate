package ru.yandex.practicum.filmorate.exception;

public class IdNegativeException extends RuntimeException {
    public IdNegativeException(String message) {
        super(message);
    }
}
