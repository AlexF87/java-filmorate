package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDaoTest {

    private final FilmDao filmDao;
    private final GenreDao genreDao;

    @Test
    void findGenresByIdTest() {
        Optional<Genre> genreOptional = Optional.of(genreDao.getGenreById(5));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre -> assertThat(genre)
                        .hasFieldOrPropertyWithValue("id", 5));
    }

    @Test
    void testGetAllGenres() {
        Collection<Genre> genre = genreDao.getAllGenres();
        assertThat(genre).hasSize(6);
    }

    @Test
    void testGetFilmGenres() {
        List<Genre> genres = new ArrayList<>();
        Genre genre = Genre.builder()
                .id(1)
                .name("Комедия")
                .build();
        genres.add(genre);
        Film film = filmDao.createFilm(
                Film.builder()
                        .name("AAAAA")
                        .description("ASDSDsdf adsfsd")
                        .duration(100)
                        .releaseDate(LocalDate.parse("2000-05-05"))
                        .genres(genres)
                        .mpa(Mpa.builder().id(5).build())
                        .build());
        genreDao.addFilmGenres(film.getId(), genres);
        List<Genre> filmGenres = genreDao.getFilmGenres(film.getId());
        assertThat(filmGenres).hasSize(1);
    }
}
