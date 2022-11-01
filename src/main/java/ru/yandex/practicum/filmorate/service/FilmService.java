package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdNegativeException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.dao.FilmDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class FilmService {

    private final FilmDao filmDao;
    private final UserService userService;
    private final GenreService genreService;
    @Autowired
    public FilmService(FilmDao filmDao, UserService userService, GenreService genreService) {
        this.filmDao = filmDao;
        this.userService = userService;
        this.genreService = genreService;
    }

    //Создание фильма

    public Film create (Film film) {
        validateObj(film);
        film = filmDao.createFilm(film);
        addGenreToFilm(film);
        return getFilm(film.getId());
    }

    //Обновление фильма
    public Film update (Film film) {
        checkFilmId(film.getId());
        if (checkFilmInDB(film.getId())) {
            filmDao.updateFilm(film);
            film = addGenreToFilm(film);
            return getFilm(film.getId());
        } else {
            throw new NotFoundException("the film with id= " + film.getId() + " does not exist.");
        }
    }

    //Получение всех записей
    public List<Film> getAllRecords() {
        List<Film> films = filmDao.getAllFilms();
        return addGenresToFilms(films);
    }

    //Добавление лайка к фильму
    public void addLike(long id, long userId) {
        if (userService.checkUserInDB(userId) && checkFilmInDB(id)) {
            if (!filmDao.checkLikeFromUser(id, userId)) {
                filmDao.saveLike(id, userId);
            }
        } else {
            throw new NotFoundException("the user with id= " + userId + "or film with id= " + id +
                    " does not exist.");
        }
    }

    //Удаление лайка к фильму
    public void deleteLike(long id, long userId) {
        if (userService.checkUserInDB(userId) && checkFilmInDB(id)) {
            if (checkLikeFromUser(userId, id)) {
                filmDao.deleteLike(id, userId);
            }
        } else {
            throw new NotFoundException("the user with id= " + userId + "or film with id= " + id +
                    " does not exist.");
        }
    }

    //Возвращает список из первых count фильмов
    public List<Film> getListPopularFilms(int count) {
        List<Film> sortedListForFilms = filmDao.getPopularFilms(count);
        return addGenresToFilms(sortedListForFilms);
    }

    //Возвращает фильм
    public Film getFilm(long id) {
        checkFilmId(id);
        if (checkFilmInDB(id)) {
            Film film = filmDao.getFilm(id);
            List<Genre> genres = genreService.getFilmGenres(film.getId());
            if (genres.size() > 0) {
                film.setGenres(genres);
            }
            return film;
        } else {
            throw new NotFoundException("the film with id= " + id + " does not exist.");
        }
    }
    private void checkFilmId(long id) {
        if (id < 0) {
            throw new FilmIdNegativeException("id должно быть положительным");
        }
    }
    private boolean checkFilmInDB (Long id) {
        return filmDao.checkFilmInDB(id);
    }
    private boolean checkLikeFromUser(Long userId, Long filmId) {
        return filmDao.checkLikeFromUser(userId, filmId);
    }

    //Валидация
    public void validateObj(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза раньше 28.12.1895");
        }
    }

    private Film addGenreToFilm(Film film) {
        if (film.getGenres()!= null) {
            List<Genre> genresFilm = new ArrayList<>();

            for (int i = 0; i < film.getGenres().size(); i++) {
                genresFilm.add(genreService.getGenreById(film.getGenres().get(i).getId()));
            }
            genreService.addFilmGenres(film.getId(), genresFilm);
            film.setGenres(genresFilm);
        } else {
            List<Genre> genres = new ArrayList<>();
            film.setGenres(genres);
        }
        return film;
    }

    private List<Film> addGenresToFilms(List<Film> filmList) {
        if(filmList != null && filmList.size() > 0) {
            for (Film ob : filmList) {
                List<Genre> genres = genreService.getFilmGenres(ob.getId());
                if (genres.size() > 0) {
                    ob.setGenres(genres);
                }
            }
        }
        return filmList;
    }
}
