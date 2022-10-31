package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoTest {

    private final UserDao userDao;
    private final LikesDao likesDao;
    private final FilmDao filmDao ;

    static Film film;
    static List<Genre> genres;
    static User user;

    @BeforeAll
    static void init() {
        Genre genre = Genre.builder()
                .id(1)
                .name("Комедия").build();
        genres = new ArrayList<>();

        genres.add(genre);

        film = Film.builder()
                .name("NNN")
                .description("asdsd")
                .releaseDate(LocalDate.of(1900, 05, 06))
                .duration(15)
                .genres(genres)
                .mpa(Mpa.builder().id(1).name("G").build())
                .build();

        user = User.builder()
                .email("qwe@qwe.er")
                .login("admin")
                .name("Kto")
                .birthday(LocalDate.of(2000, 1, 15))
                .build();
    }
    @BeforeEach
    void init2() {
        filmDao.createFilm(film);
        userDao.createUser(user);
    }
    @AfterEach
    void clear() {
        filmDao.deleteFilm(film.getId());
        userDao.deleteUser(user.getId());
    }

    @Test
    void testCreateFilm() {
        Optional<Film> filmOptional = Optional.of(filmDao.getFilm(film.getId()));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("duration", 15)
                );
        assertThat(filmDao.checkFilmInDB(film.getId())).isTrue();
    }

    @Test
    void testGetAllFilms() {
        List<Film> films = filmDao.getAllFilms();
        assertThat(films.size()).isEqualTo(1);
    }

    @Test
    void testUpdateFilm() {
        Film newFilm = Film.builder()
                .id(film.getId())
                .name("Hello")
                .description("asdsd")
                .releaseDate(LocalDate.of(1900, 05, 06))
                .duration(15)
                .genres(genres)
                .mpa(Mpa.builder().id(1).name("G").build())
                .build();
        filmDao.updateFilm(newFilm);
        Film filmRep = filmDao.getFilm(film.getId());
        assertThat(filmRep.getName()).isEqualTo("Hello");
    }

    @Test
    void testSaveLike() {
        filmDao.saveLike(film.getId(), user.getId());
        assertThat(filmDao.checkLikeFromUser(film.getId(), user.getId())).isTrue();
        filmDao.saveLike(film.getId(), user.getId());
        assertThat(filmDao.checkLikeFromUser(film.getId(), user.getId())).isTrue();
        filmDao.deleteLike(film.getId(), user.getId());
        assertThat(filmDao.checkLikeFromUser(film.getId(), user.getId())).isFalse();
    }

    @Test
    void testGetPopularFilms() {
        filmDao.saveLike(film.getId(), user.getId());
        List<Film> films = filmDao.getPopularFilms(1);
        assertThat(films.size()).isEqualTo(1);
    }

    @Test
    void testLike() {
        likesDao.addLike(film.getId(), user.getId());
        Collection<Likes> likes = likesDao.getAllLikes(film.getId());
        assertThat(likes.size()).isEqualTo(1);
        likesDao.deleteLike(film.getId(), user.getId());
        likes = likesDao.getAllLikes(film.getId());
        assertThat(likes.size()).isEqualTo(0);
    }
}
