package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Получить пользователя по id
    @Override
    public User getUser(Long id) {
        String sql = "SELECT * FROM users WHERE users_id = ?;";
       return  jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUsers(rs, rowNum), id);
    }

    //Создать пользователя
    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"users_id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    //Обновить пользователя
    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET email = ?, login = ?, name = ?,  birthday = ? WHERE users_id = ?;";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    //Получить список всех пользователей
    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUsers(rs, rowNum));
    }

    private User makeUsers(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("users_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    //Удалить всех пользователей
    @Override
    public void deleteUsers() {
        String sql = "DELETE FROM users";
        jdbcTemplate.update(sql);
    }

    //Удалить пользователя
    @Override
    public void deleteUser(long id) {
        String sql = "DELETE FROM users WHERE users_id = ?;";
        jdbcTemplate.update(sql, id);
    }

    //Проверить пользователя на наличие в БД
    @Override
    public boolean checkUserInDB(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE users_id = ?;", id);
        if (userRows.next()) {
            return true;
        } else {
            return false;
        }

    }
}
