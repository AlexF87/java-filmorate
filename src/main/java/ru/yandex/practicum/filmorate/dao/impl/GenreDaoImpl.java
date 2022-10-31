package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Получить жанр по id
    @Override
    public Genre getGenreById(Integer id) {
        String sql = "SELECT genre_id, name_genre FROM genres WHERE genre_id = ?;";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeGenre(rs, rowNum), id);
    }

    //Получить все жанры
    @Override
    public Collection<Genre> getAllGenres() {
        String sql = "SELECT genre_id, name_genre FROM genres;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs, rowNum));
    }

    //Добавить жанры к фильму
    @Override
    public Collection<Genre> addFilmGenres(Long filmId, List<Genre> genres) {
        deleteFilmGenres(filmId);
        for (int i = 0; i < genres.size(); i++) {
            if (!checkGenreOfFilm(filmId, genres.get(i).getId())) {
                addFilmGenres(filmId, genres.get(i).getId());
            }
        }
        return genres;
    }

    //Получить все жанры данного фильма
    @Override
    public List<Genre> getFilmGenres(Long filmId) {
        String sql = "SELECT g.genre_id, g.name_genre " +
                "FROM genres AS g " +
                "RIGHT JOIN films_genres AS fg ON g.genre_id = fg.genres_id " +
                "WHERE fg.film_id = ?;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs, rowNum), filmId);
    }

    //Удалить у фильма жанры
    @Override
    public void deleteFilmGenres(Long filmId) {
        String sql = "DELETE FROM films_genres WHERE film_id = ?;";
        jdbcTemplate.update(sql, filmId);
    }

    //Проверка наличия жанра у фильма
    private boolean checkGenreOfFilm(Long filmId, Integer genreId) {
        String sql = "SELECT COUNT(genres_id) FROM films_genres WHERE film_id = ? AND genres_id = ?;";
        int quantity = jdbcTemplate.queryForObject(sql, Integer.class, filmId, genreId);
        return quantity > 0;
    }

    //Добавление фильму жанра
    private void addFilmGenres(Long filmId, Integer genresId) {
        String sql = "INSERT INTO films_genres (film_id, genres_id) VALUES (?, ?);";
        jdbcTemplate.update(sql, filmId, genresId);
    }

    private Genre makeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name_genre"))
                .build();
    }
}
