package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreService = genreService;
    }

    //Создать фильм
    @Override
    public Film createFilm(Film film) {
        String sql = "INSERT INTO films (film_name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"film_id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setLong(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;
    }

    //Обновить фильм
    @Override
    public void updateFilm(Film film) {
        String sql = "UPDATE films " +
                "SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE film_id = ?;";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());
    }

    //Получить филь по id
    @Override
    public Film getFilm(Long id) {
        String sql =
                "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.mpa_id, m.rating_name " +
                        "FROM films AS f " +
                        "JOIN mpa_rating AS m ON m.mpa_id = f.mpa_id " +
                        "WHERE f.film_id = ?;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeFilm(rs, rowNum), id);
    }

    private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Mpa mpa = Mpa.builder()
                .id(resultSet.getInt("mpa_id"))
                .name(resultSet.getString("rating_name"))
                .build();
        return Film.builder()
                .id(resultSet.getLong("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(mpa)
                .genres(new ArrayList<>())
                .build();
    }

    //Получить все фильмы
    @Override
    public List<Film> getAllFilms() {
        String sql =
                "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.Mpa_id, m.rating_name " +
                "FROM films f " +
                "JOIN mpa_rating AS m ON m.mpa_id = f.Mpa_id; ";
        return jdbcTemplate.query(sql,(rs, rowNum) -> makeFilms(rs, rowNum));
    }

    public Film makeFilms(ResultSet resultSet, int rowNum) throws SQLException {
        Mpa mpa = Mpa.builder()
                .id(resultSet.getInt("mpa_id"))
                .name(resultSet.getString("RATING_NAME"))
                .build();
        return Film.builder()
                .id(resultSet.getLong("film_id"))
                .name(resultSet.getString("film_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa (mpa)
                .genres(new ArrayList<>())
                .build();
    }

    //Сохранить лайк
    @Override
    public void saveLike(Long filmId, Long userId) {
        String sql = "INSERT INTO users_likes (film_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(sql, filmId, userId);
    }

    //Удалить лайк
    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM users_likes WHERE film_id = ? AND user_id = ?;";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    //Проверить лайк пользователя
    @Override
    public boolean checkLikeFromUser(Long filmId, Long userId) {
        String sqlQuery = "SELECT COUNT(user_id) " +
                "FROM users_likes " +
                "WHERE film_id = ? AND user_id = ?;";
        int like = jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId, userId);
        return 0 < like;
    }

    //Получить популярные фильмы
    @Override
    public List<Film> getPopularFilms(int count) {
        String sqlQuery =
                "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.Mpa_id, m.rating_name , " +
                        "FROM films AS f " +
                        "JOIN mpa_rating AS m ON m.mpa_id = f.mpa_id " +
                        "LEFT JOIN (SELECT film_id, COUNT(user_id) rating " +
                        "      FROM users_likes " +
                        "      GROUP BY film_id) AS r ON f.film_id =  r.film_id " +
                        "ORDER BY r.rating DESC " +
                        "LIMIT ?;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilms(rs, rowNum), count);
    }

    //Проверить фильмы в БД
    @Override
    public boolean checkFilmInDB(Long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE film_id = ?;", id);
        if (filmRows.next()) {
            return true;
        } else {
            return false;
        }
    }
    //Удаление фильма
    @Override
    public void deleteFilm(Long id) {
        String sql = "DELETE FROM films WHERE film_id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
