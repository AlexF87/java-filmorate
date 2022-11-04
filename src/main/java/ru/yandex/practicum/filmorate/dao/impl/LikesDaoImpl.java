package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.model.Likes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class LikesDaoImpl implements LikesDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Добавить лайк фильму
    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO users_likes (film_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    //Получить все лайки данного фильма
    @Override
    public Collection<Likes> getAllLikes(Long filmId) {
        String sql = "SELECT * FROM users_likes WHERE film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs, rowNum), filmId);
    }

    //Удалить лайк
    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM users_likes WHERE film_id=? AND user_id=?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    private Likes makeLike(ResultSet resultSet, int rowNum) throws SQLException {
        return Likes.builder()
                .likeId(resultSet.getInt("users_likes_id"))
                .filmId(resultSet.getInt("film_id"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }

}
