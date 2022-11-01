package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FilmIdNegativeException;
import ru.yandex.practicum.filmorate.exception.IdNegativeException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("Ошибка 404 {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus. BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.info("Ошибка 400 {}", e.getMessage());
        return new ErrorResponse((e.getMessage()));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus. BAD_REQUEST)
    public ErrorResponse handleValidationException(final FilmIdNegativeException e) {
        log.info("Ошибка 400 {}", e.getMessage());
        return new ErrorResponse((e.getMessage()));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleValidationException(final IdNegativeException e) {
        log.info("Ошибка 404 {}", e.getMessage(), e);
        return new ErrorResponse((e.getMessage()));
    }
}
