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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    private final FilmDao filmDao;
    private final UserService userService;
    private final GenreService genreService;

    private final FilmsGenresServiсe filmsGenresServiсe;
    @Autowired
    public FilmService(FilmDao filmDao, UserService userService, GenreService genreService,
                       FilmsGenresServiсe filmsGenresServiсe) {
        this.filmDao = filmDao;
        this.userService = userService;
        this.genreService = genreService;
        this.filmsGenresServiсe = filmsGenresServiсe;
    }

    //Создание фильма
    public Film create (Film film) {
        validateObj(film);
        film = filmDao.createFilm(film);
        genreService.writeFilmGenres(film.getId(), film.getGenres());
        return getFilm(film.getId());
    }

    //Обновление фильма
    public Film update (Film film) {
        checkFilmId(film.getId());
        if (checkFilmInDB(film.getId())) {
            filmDao.updateFilm(film);
            genreService.addFilmGenres(film.getId(), film.getGenres());
            return getFilm(film.getId());
        } else {
            throw new NotFoundException("the film with id= " + film.getId() + " does not exist.");
        }
    }

    //Получение всех записей
    public List<Film> getAllRecords() {
        List<Film> filmsAll = filmDao.getAllFilms();
        String idFilms = new String();
        Map<Long, Film> films = new HashMap<>();

        for (Film film : filmsAll) {
            idFilms = idFilms +" "+ film.getId() + ",";
            films.put(film.getId(), film);
        }
        if (idFilms.length() > 0) {
            idFilms = idFilms.substring(1, idFilms.length() - 1);
        }
        return filmsGenresServiсe.getGenresForGivenIds(idFilms, films);
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
        Map<Long, Film> films = new HashMap<>();
        String idFilms = new String();

        for (Film film : sortedListForFilms) {
            idFilms = idFilms +" "+ film.getId() + ",";
            films.put(film.getId(), film);
        }
        if (idFilms.length() > 0) {
            idFilms = idFilms.substring(1, idFilms.length() - 1);
        }
       return   filmsGenresServiсe.getGenresForGivenIds(idFilms, films);


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

    //Проверка id фильма
    private void checkFilmId(long id) {
        if (id < 0) {
            throw new FilmIdNegativeException("id должно быть положительным");
        }
    }

    //Проверка наличия фильма в БД
    private boolean checkFilmInDB (Long id) {
        return filmDao.checkFilmInDB(id);
    }

    //Проверка лайка пользователя
    private boolean checkLikeFromUser(Long userId, Long filmId) {
        return filmDao.checkLikeFromUser(userId, filmId);
    }

    //Валидация
    public void validateObj(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза раньше 28.12.1895");
        }
    }
}
