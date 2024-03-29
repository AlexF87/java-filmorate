package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendsDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendsDaoImpl implements FriendsDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Добавить в друзья
    @Override
    public void createFriend(Long userId, Long friendId) {
        String sql = "INSERT INTO friends (user_id, friend_id)" +
                "VALUES (?, ?)";

        jdbcTemplate.update(sql, userId, friendId);
    }

    //Удалить дружбу с между пользователями
    @Override
    public void deleteFriend(Long userId, Long friendId) {
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id=?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    //Получить друзей пользователя
    @Override
    public List<User> getFriendsOfUser(Long id) {
        String sql = "SELECT u.users_id, u.email, u.login, u.name, u.birthday " +
                "FROM friends AS f " +
                "JOIN  users AS u ON f.friend_id = u.users_id " +
                "WHERE f.user_id = ?;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriendsOfUser(rs, rowNum), id);
    }

    private User makeFriendsOfUser(ResultSet resultSet, int row) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("users_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    //Получить общих друзей, между пользователями
    @Override
    public List<User> getCommonsFriend(long idFirst, long idSecond) {
        String sql = "SELECT u.* FROM users as u " +
                "JOIN (SELECT f.friend_id  FROM friends AS f " +
                " JOIN (SELECT f.friend_id AS id FROM friends AS f WHERE f.user_id = ?) " +
                "AS fr ON f.FRIEND_ID = fr.id WHERE f.user_id = ?) " +
        "AS fr ON fr.FRIEND_ID = u.USERS_ID;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriendsOfUser(rs, rowNum), idFirst, idSecond);
    }
}
