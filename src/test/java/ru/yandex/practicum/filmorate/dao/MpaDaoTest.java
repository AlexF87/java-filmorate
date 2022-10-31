package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDaoTest {

      private final MpaDao mpaDao;

    @Test
    void testGetMpaByIdTest() {
        Optional<Mpa> genreOptional = Optional.of(mpaDao.getMpa(5));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> assertThat(mpa)
                        .hasFieldOrPropertyWithValue("name", "NC-17"));
    }

    @Test
    void testGetAll() {
        Collection<Mpa> mpaAll = mpaDao.getAll();
        assertThat(mpaAll).hasSize(5);
    }

}
