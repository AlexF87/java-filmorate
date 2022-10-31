package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Получить рейтинг по id
    @Override
    public Mpa getMpa(Integer id) {
        String sql = "SELECT mpa_id, rating_name FROM mpa_rating WHERE mpa_id = ?;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMpa(rs, rowNum), id);
    }

    //Получить все рейтинги
    @Override
    public Collection<Mpa> getAll() {
        String sql = "SELECT mpa_id, rating_name FROM mpa_rating;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs, rowNum));
    }

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("mpa_id"))
                .name(resultSet.getString("rating_name"))
                .build();
    }
}
