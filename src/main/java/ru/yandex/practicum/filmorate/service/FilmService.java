package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage inMemoryFilmStorage;

    private final UserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage, UserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

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
        if (checkFilmInStorage(film.getId())) {
            return inMemoryFilmStorage.update(film);
        } else {
            throw new NotFoundException("the film with id= " + film.getId() + " does not exist.");
        }
    }

    //Получение всех записей
    public List<Film> getAllRecords() {
        return inMemoryFilmStorage.getAllRecords();
    }

    //Добавление лайка к фильму
    public void addLike(long id, long userId) {
        if (checkUserInStorage(userId) && checkFilmInStorage(id)) {
            Film film = inMemoryFilmStorage.getFilm(id);
            film.getUserLikes().add(userId);
        } else {
            throw new NotFoundException("the user with id= " + userId + "or film with id= " + id +
                    " does not exist.");
        }
    }

    //Удаление лайка к фильму
    public void deleteLike(long id, long userId) {
        if (checkUserInStorage(userId) && checkFilmInStorage(id)) {
            inMemoryFilmStorage.getFilm(id).getUserLikes().remove(userId);
        } else {
            throw new NotFoundException("the user with id= " + userId + "or film with id= " + id +
                    " does not exist.");
        }
    }

    //Возвращает список из первых count фильмов
    public List<Film> getListPopularFilms(int count) {
        List<Film> sortedListForFilms = inMemoryFilmStorage.getAllRecords().stream()
                .sorted(Comparator.comparing(x->-x.getUserLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
        return sortedListForFilms;
    }

    //Возвращает фильм
    public Film getFilm(long id) {
        if (checkFilmInStorage(id)) {
            return inMemoryFilmStorage.getFilm(id);
        } else {
            throw new NotFoundException("The film with id= " + id + " does not exist.");
        }
    }
    private boolean checkFilmInStorage(long id) {
        final List<Film> allRecords = inMemoryFilmStorage.getAllRecords();
        if (allRecords.stream().anyMatch(s->s.getId() == id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserInStorage(long id) {
        final List<User> allRecords = inMemoryUserStorage.getAllRecords();
        if (allRecords.stream().anyMatch(s -> s.getId() == id)) {
            return true;
        } else {
            return false;
        }
    }


}
